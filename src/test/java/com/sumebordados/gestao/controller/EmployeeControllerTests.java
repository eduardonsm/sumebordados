package com.sumebordados.gestao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumebordados.gestao.dto.employee.EmployeeRequestDTO;
import com.sumebordados.gestao.dto.employee.EmployeeResponseDTO;
import com.sumebordados.gestao.model.Employee;
import com.sumebordados.gestao.model.enums.EmployeeRole;
import com.sumebordados.gestao.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do Controlador de Funcionários")
public class EmployeeControllerTests {

    final String URI_EMPLOYEES = "/employees";

    @Autowired
    MockMvc driver;

    @Autowired
    EmployeeRepository employeeRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Employee employee;
    EmployeeRequestDTO employeeRequestDTO;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();

        employee = employeeRepository.save(new Employee(
                "Carlos Silva",
                "carlos.silva",
                "senha123",
                EmployeeRole.EMPLOYEE
        ));

        employeeRequestDTO = new EmployeeRequestDTO(
                "Carlos Silva",
                "carlos.silva",
                "senha123",
                EmployeeRole.EMPLOYEE
        );
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de Criação de Funcionário")
    class EmployeeCriacao {

        @Test
        @DisplayName("Quando criamos um novo funcionário com dados válidos")
        void quandoCriarEmployeeValido() throws Exception {
            EmployeeRequestDTO novoFuncionario = new EmployeeRequestDTO(
                    "Ana Pereira",
                    "ana.pereira",
                    "segredo",
                    EmployeeRole.ADMIN
            );

            String responseJsonString = driver.perform(post(URI_EMPLOYEES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(novoFuncionario)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EmployeeResponseDTO resultado = objectMapper.readValue(responseJsonString, EmployeeResponseDTO.class);

            assertAll(
                    () -> assertNotNull(resultado.id()),
                    () -> assertEquals(novoFuncionario.username(), resultado.username()),
                    () -> assertEquals(novoFuncionario.role(), resultado.role())
            );
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de Atualização de Funcionário")
    class EmployeeAtualizacao {

        @Test
        @DisplayName("Quando alteramos um funcionário com dados válidos")
        void quandoAlteramosEmployeeValido() throws Exception {
            EmployeeRequestDTO dtoAtualizacao = new EmployeeRequestDTO(
                    "Carlos Alterado",
                    "carlos.novo",
                    "senhaNova",
                    EmployeeRole.ADMIN
            );

            String responseJsonString = driver.perform(put(URI_EMPLOYEES + "/" + employee.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dtoAtualizacao)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EmployeeResponseDTO resultado = objectMapper.readValue(responseJsonString, EmployeeResponseDTO.class);

            assertAll(
                    () -> assertEquals(employee.getId(), resultado.id()),
                    () -> assertEquals(dtoAtualizacao.username(), resultado.username()),
                    () -> assertEquals(dtoAtualizacao.role(), resultado.role())
            );
        }

        @Test
        @DisplayName("Quando tentamos alterar um funcionário inexistente")
        void quandoAlteramosEmployeeInexistente() throws Exception {
            EmployeeRequestDTO dtoAtualizacao = new EmployeeRequestDTO(
                    "Fantasma", "ghost", "000", EmployeeRole.EMPLOYEE
            );

            String responseString = driver.perform(put(URI_EMPLOYEES + "/" + 99999L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dtoAtualizacao)))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertTrue(responseString.contains("Employee with id 99999 not found"));
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de Remoção de Funcionário")
    class EmployeeRemocao {

        @Test
        @DisplayName("Quando excluímos um funcionário salvo")
        void quandoExcluimosEmployeeValido() throws Exception {
            driver.perform(delete(URI_EMPLOYEES + "/" + employee.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andDo(print());

            assertTrue(employeeRepository.findById(employee.getId()).isEmpty());
        }

        @Test
        @DisplayName("Quando tentamos excluir um funcionário inexistente")
        void quandoExcluimosEmployeeInexistente() throws Exception {
            String responseString = driver.perform(delete(URI_EMPLOYEES + "/" + 99999L)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertTrue(responseString.contains("Employee with id 99999 not found"));
        }
    }
}