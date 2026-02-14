package com.sumebordados.gestao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sumebordados.gestao.dto.customer.CustomerRequestDTO;
import com.sumebordados.gestao.dto.customer.CustomerResponseDTO;
import com.sumebordados.gestao.dto.customer.DeleteCustomerResponseDTO;
import com.sumebordados.gestao.model.Customer;
import com.sumebordados.gestao.repository.CustomerRepository;
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
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("Testes do Controlador de Clientes")
public class CustomerControllerTests {

    final String URI_CUSTOMERS = "/customers";

    @Autowired
    MockMvc driver;

    @Autowired
    CustomerRepository customerRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Customer customer;
    CustomerRequestDTO customerRequestDTO;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());

        // Limpa o banco antes de cada teste para garantir isolamento
        customerRepository.deleteAll();

        customer = customerRepository.save(new Customer(
                "Maria da Silva",
                "83999998888",
                "Rua das Flores, 123"
        ));

        customerRequestDTO = new CustomerRequestDTO(
                "Maria da Silva",
                "83999998888",
                "Rua das Flores, 123"
        );
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de Criação de Cliente")
    class ClienteCriacao {

        @Test
        @DisplayName("Quando criamos um novo cliente com dados válidos")
        void quandoCriarClienteValido() throws Exception {
            CustomerRequestDTO novoCliente = new CustomerRequestDTO(
                    "João Souza", "83988887777", "Av. Central, 500"
            );

            String responseJsonString = driver.perform(post(URI_CUSTOMERS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(novoCliente)))
                    .andExpect(status().isCreated()) // Espera 201 Created
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomerResponseDTO resultado = objectMapper.readValue(responseJsonString, CustomerResponseDTO.class);

            assertAll(
                    () -> assertNotNull(resultado.id()),
                    () -> assertEquals(novoCliente.nome(), resultado.nome()),
                    () -> assertEquals(novoCliente.telefone(), resultado.telefone())
            );
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de Atualização de Cliente")
    class ClienteAtualizacao {

        @Test
        @DisplayName("Quando alteramos um cliente com dados válidos")
        void quandoAlteramosClienteValido() throws Exception {
            CustomerRequestDTO dtoAtualizacao = new CustomerRequestDTO(
                    "Maria Alterada",
                    "83999990000",
                    "Rua Nova, 100"
            );

            String responseJsonString = driver.perform(put(URI_CUSTOMERS + "/" + customer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dtoAtualizacao)))
                    .andExpect(status().isOk()) // Espera 200 OK
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomerResponseDTO resultado = objectMapper.readValue(responseJsonString, CustomerResponseDTO.class);

            assertAll(
                    () -> assertEquals(customer.getId(), resultado.id()),
                    () -> assertEquals(dtoAtualizacao.nome(), resultado.nome()),
                    () -> assertEquals(dtoAtualizacao.endereco(), resultado.endereco())
            );
        }

        @Test
        @DisplayName("Quando tentamos alterar um cliente inexistente")
        void quandoAlteramosClienteInexistente() throws Exception {
            CustomerRequestDTO dtoAtualizacao = new CustomerRequestDTO(
                    "Fantasma", "00000000", "Rua X"
            );

            String responseString = driver.perform(put(URI_CUSTOMERS + "/" + 99999L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dtoAtualizacao)))
                    .andExpect(status().isNotFound()) // Espera 404
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // O GlobalException retorna apenas uma String no body
            assertEquals("Customer with id 99999 not found", responseString);
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de Remoção de Cliente")
    class ClienteRemocao {

        @Test
        @DisplayName("Quando excluímos um cliente salvo")
        void quandoExcluimosClienteValido() throws Exception {
            String responseJsonString = driver.perform(delete(URI_CUSTOMERS + "/" + customer.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()) // Seu controller retorna 200 OK com o objeto deletado
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            DeleteCustomerResponseDTO resultado = objectMapper.readValue(responseJsonString, DeleteCustomerResponseDTO.class);

            assertAll(
                    () -> assertEquals(customer.getId(), resultado.id()),
                    () -> assertEquals(customer.getNome(), resultado.nome()),
                    () -> assertTrue(customerRepository.findById(customer.getId()).isEmpty()) // Garante que saiu do banco
            );
        }

        @Test
        @DisplayName("Quando tentamos excluir um cliente inexistente")
        void quandoExcluimosClienteInexistente() throws Exception {
            String responseString = driver.perform(delete(URI_CUSTOMERS + "/" + 99999L)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertEquals("Customer with id 99999 not found", responseString);
        }
    }
}