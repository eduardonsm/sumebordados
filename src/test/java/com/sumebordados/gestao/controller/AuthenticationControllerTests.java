package com.sumebordados.gestao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumebordados.gestao.dto.auth.AuthenticationDTO;
import com.sumebordados.gestao.infra.security.TokenService;
import com.sumebordados.gestao.model.Employee;
import com.sumebordados.gestao.model.enums.EmployeeRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes de Autenticação")
class AuthenticationControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AuthenticationManager authenticationManager;

    @MockitoBean
    TokenService tokenService;

    @Test
    @DisplayName("Deve retornar 200 e Token quando credenciais são válidas")
    void loginComSucesso() throws Exception {
        // Arrange
        AuthenticationDTO loginData = new AuthenticationDTO("admin", "123456");

        // Simula o objeto Employee retornado após autenticação
        Employee employeeAutenticado = new Employee("Admin", "admin", "encryptedPass", EmployeeRole.ADMIN);
        Authentication authMock = mock(Authentication.class);

        when(authMock.getPrincipal()).thenReturn(employeeAutenticado);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authMock);

        when(tokenService.generateToken(any(Employee.class))).thenReturn("token-jwt-falso");

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-jwt-falso"))
                .andDo(print());
    }

    @Test
    @DisplayName("Deve retornar 403/401 quando falha na autenticação")
    void loginComFalha() throws Exception {
        // Arrange
        AuthenticationDTO loginData = new AuthenticationDTO("invalido", "0000");

        // Simula erro no AuthenticationManager
        when(authenticationManager.authenticate(any()))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("Bad credentials"));

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData)))
                .andExpect(status().isForbidden()) // O Spring Security costuma retornar 401 ou 403 dependendo da config
                .andDo(print());
    }
}