package com.sumebordados.gestao.infra.security;

import com.sumebordados.gestao.model.Employee;
import com.sumebordados.gestao.model.enums.EmployeeRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    private TokenService tokenService;

    @BeforeEach
    void setup() {
        tokenService = new TokenService();
        // Injeta o segredo manualmente para o teste não depender do application.properties
        ReflectionTestUtils.setField(tokenService, "secret", "minha-chave-secreta-teste");
    }

    @Test
    @DisplayName("Deve gerar um token válido para um funcionário")
    void generateToken() {
        Employee employee = new Employee("Teste", "teste.user", "123", EmployeeRole.EMPLOYEE);

        String token = tokenService.generateToken(employee);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    @DisplayName("Deve validar token e retornar o username correto")
    void validateToken_Success() {
        Employee employee = new Employee("Teste", "usuario.correto", "123", EmployeeRole.EMPLOYEE);
        String token = tokenService.generateToken(employee);

        String subject = tokenService.validateToken(token);

        assertEquals("usuario.correto", subject);
    }

    @Test
    @DisplayName("Deve retornar string vazia ao validar token inválido")
    void validateToken_Error() {
        String tokenInvalido = "token.invalido.aleatorio";

        String subject = tokenService.validateToken(tokenInvalido);

        assertEquals("", subject);
    }
}