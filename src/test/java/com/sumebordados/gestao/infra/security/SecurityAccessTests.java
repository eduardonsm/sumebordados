package com.sumebordados.gestao.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumebordados.gestao.dto.employee.EmployeeRequestDTO;
import com.sumebordados.gestao.model.enums.EmployeeRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes de Controle de Acesso (Security)")
class SecurityAccessTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 403 Forbidden ao tentar criar funcionário sem token")
    void deveNegarAcessoSemToken() throws Exception {
        EmployeeRequestDTO novoFuncionario = new EmployeeRequestDTO(
                "Hacker", "hacker", "123", EmployeeRole.ADMIN
        );

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoFuncionario)))
                .andExpect(status().isForbidden());
    }

    /* Observação: Para testar o "caminho feliz" (acesso permitido com token),
       seria necessário mockar o TokenService e o EmployeeRepository para que
       o SecurityFilter valide o token e injete o usuário no contexto.
       Como isso é complexo em teste de integração puro, geralmente testamos
       o bloqueio (403) aqui e o fluxo completo nos testes de Controller individuais
       usando @WithMockUser se necessário.
    */
}