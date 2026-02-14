package com.sumebordados.gestao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sumebordados.gestao.dto.order.OrderRequestDTO;
import com.sumebordados.gestao.dto.order.OrderResponseDTO;
import com.sumebordados.gestao.dto.order.OrderSizeRequestDTO;
import com.sumebordados.gestao.exception.OrderNotFoundException;
import com.sumebordados.gestao.model.Customer;
import com.sumebordados.gestao.model.Order;
import com.sumebordados.gestao.model.enums.BaseSizeType;
import com.sumebordados.gestao.model.enums.OrderStatus;
import com.sumebordados.gestao.model.enums.VariantType;
import com.sumebordados.gestao.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DisplayName("Testes do Controlador de Pedidos")
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTests {

    final String URI_ORDERS = "/orders";

    @Autowired
    MockMvc driver;

    @MockitoBean
    OrderService orderService;

    ObjectMapper objectMapper = new ObjectMapper();

    OrderRequestDTO orderRequestDTO;
    OrderResponseDTO orderResponseDTO;
    Order order;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());

        Set<String> colors = new HashSet<>();
        colors.add("Azul Marinho");
        Set<OrderSizeRequestDTO> sizes = new HashSet<>();
        sizes.add(new OrderSizeRequestDTO(BaseSizeType.M, VariantType.NORMAL, 10));

        orderRequestDTO = new OrderRequestDTO(
                1L, // Customer ID
                "Camisa Polo",
                "Piquet",
                true,
                10,
                1, 0, 0,
                50.0f, 500.0f,
                LocalDate.now().plusDays(15),
                LocalDate.now(),
                250.0f, 250.0f,
                OrderStatus.AGUARDANDO_ARTE,
                "http://arte.url",
                colors,
                sizes
        );

        orderResponseDTO = new OrderResponseDTO(100L);

        order = new Order();
        order.setId(100L);
        order.setModel("Camisa Polo");
        order.setStatus(OrderStatus.AGUARDANDO_ARTE);
        order.setCustomer(new Customer("Cliente Teste", "99999999", "Rua Teste"));
    }

    @Nested
    @DisplayName("Criação de Pedidos")
    class OrderCriacao {

        @Test
        @DisplayName("Deve criar pedido com sucesso e retornar 201 Created")
        void quandoCriarPedidoValido() throws Exception {
            // Arrange
            when(orderService.createOrder(any(OrderRequestDTO.class))).thenReturn(orderResponseDTO);

            // Act & Assert
            driver.perform(post(URI_ORDERS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(orderRequestDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"))
                    .andExpect(jsonPath("$.id").value(100L))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("Busca de Pedidos")
    class OrderBusca {

        @Test
        @DisplayName("Deve retornar pedido quando ID existente")
        void quandoBuscarPedidoExistente() throws Exception {
            // Arrange
            when(orderService.getOrderById(100L)).thenReturn(order);

            // Act & Assert
            driver.perform(get(URI_ORDERS + "/{id}", 100L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(100L))
                    .andExpect(jsonPath("$.model").value("Camisa Polo"))
                    .andExpect(jsonPath("$.status").value(OrderStatus.AGUARDANDO_ARTE.toString()))
                    .andDo(print());
        }

        @Test
        @DisplayName("Deve retornar 404 quando pedido não existe")
        void quandoBuscarPedidoInexistente() throws Exception {
            // Arrange
            when(orderService.getOrderById(999L)).thenThrow(new OrderNotFoundException(999L));

            // Act & Assert
            driver.perform(get(URI_ORDERS + "/{id}", 999L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(containsString("Order with id 999 not found")))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("Atualização de Pedidos")
    class OrderAtualizacao {

        @Test
        @DisplayName("Deve atualizar pedido com sucesso")
        void quandoAtualizarPedidoValido() throws Exception {
            // Arrange
            when(orderService.updateOrder(eq(100L), any(OrderRequestDTO.class))).thenReturn(orderResponseDTO);

            // Act & Assert
            driver.perform(put(URI_ORDERS + "/{id}", 100L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(orderRequestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(100L))
                    .andDo(print());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar atualizar pedido inexistente")
        void quandoAtualizarPedidoInexistente() throws Exception {
            // Arrange
            when(orderService.updateOrder(eq(999L), any(OrderRequestDTO.class)))
                    .thenThrow(new OrderNotFoundException(999L));

            // Act & Assert
            driver.perform(put(URI_ORDERS + "/{id}", 999L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(orderRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("Remoção de Pedidos")
    class OrderRemocao {

        @Test
        @DisplayName("Deve deletar pedido e retornar 204 No Content")
        void quandoDeletarPedidoExistente() throws Exception {
            // Arrange
            doNothing().when(orderService).deleteOrder(100L);

            // Act & Assert
            driver.perform(delete(URI_ORDERS + "/{id}", 100L))
                    .andExpect(status().isNoContent())
                    .andDo(print());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar deletar pedido inexistente")
        void quandoDeletarPedidoInexistente() throws Exception {
            // Arrange
            doThrow(new OrderNotFoundException(999L)).when(orderService).deleteOrder(999L);

            // Act & Assert
            driver.perform(delete(URI_ORDERS + "/{id}", 999L))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }
}