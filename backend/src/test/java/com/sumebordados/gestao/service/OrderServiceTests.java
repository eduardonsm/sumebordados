package com.sumebordados.gestao.service;

import com.sumebordados.gestao.dto.order.OrderRequestDTO;
import com.sumebordados.gestao.dto.order.OrderResponseDTO;
import com.sumebordados.gestao.dto.order.OrderSizeRequestDTO;
import com.sumebordados.gestao.exception.CustomerNotFoundException;
import com.sumebordados.gestao.exception.OrderNotFoundException;
import com.sumebordados.gestao.model.Customer;
import com.sumebordados.gestao.model.Order;
import com.sumebordados.gestao.model.enums.BaseSizeType;
import com.sumebordados.gestao.model.enums.OrderStatus;
import com.sumebordados.gestao.model.enums.VariantType;
import com.sumebordados.gestao.repository.CustomerRepository;
import com.sumebordados.gestao.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Serviço de Pedidos (OrderService)")
class OrderServiceTests {

    @Mock
    private OrderRepository orderRepo;

    @Mock
    private CustomerRepository customerRepo;

    @InjectMocks
    private OrderService orderService;

    private Customer customer;
    private OrderRequestDTO orderRequestDTO;

    @BeforeEach
    void setup() {
        customer = new Customer("Cliente Teste", "99999999", "Rua Teste");
        customer.setId(1L);

        Set<String> colors = new HashSet<>();
        colors.add("Azul");

        Set<OrderSizeRequestDTO> sizes = new HashSet<>();
        sizes.add(new OrderSizeRequestDTO(BaseSizeType.M, VariantType.NORMAL, 10));

        orderRequestDTO = new OrderRequestDTO(
                1L, // customerId
                "Modelo Polo",
                "Piquet",
                true,
                10,
                1, // chest
                0, // back
                0, // sleeve
                50.0f, // unit price
                500.0f, // total price
                LocalDate.now().plusDays(10),
                LocalDate.now(),
                250.0f,
                250.0f,
                OrderStatus.AGUARDANDO_ARTE,
                "http://img.url",
                colors,
                sizes
        );
    }

    @Nested
    @DisplayName("Criação de Pedidos")
    class CreateOrder {

        @Test
        @DisplayName("Deve criar pedido com sucesso quando cliente existe")
        void shouldCreateOrderSuccessfully() {
            // Arrange
            Order savedOrder = new Order();
            savedOrder.setId(100L);

            when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
            when(orderRepo.save(any(Order.class))).thenReturn(savedOrder);

            // Act
            OrderResponseDTO response = orderService.createOrder(orderRequestDTO);

            // Assert
            assertNotNull(response);
            assertEquals(100L, response.id());
            verify(orderRepo, times(1)).save(any(Order.class));
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar criar pedido para cliente inexistente")
        void shouldThrowExceptionWhenCustomerNotFound() {
            // Arrange
            when(customerRepo.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(CustomerNotFoundException.class, () -> {
                orderService.createOrder(orderRequestDTO);
            });
            verify(orderRepo, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Busca e Deleção de Pedidos")
    class GetAndDeleteOrder {

        @Test
        @DisplayName("Deve buscar pedido por ID com sucesso")
        void shouldGetOrderById() {
            Order order = new Order();
            order.setId(100L);
            when(orderRepo.findById(100L)).thenReturn(Optional.of(order));

            Order result = orderService.getOrderById(100L);

            assertEquals(100L, result.getId());
        }

        @Test
        @DisplayName("Deve lançar exceção ao buscar pedido inexistente")
        void shouldThrowExceptionWhenOrderNotFound() {
            when(orderRepo.findById(999L)).thenReturn(Optional.empty());

            assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(999L));
        }

        @Test
        @DisplayName("Deve deletar pedido com sucesso")
        void shouldDeleteOrder() {
            Order order = new Order();
            order.setId(100L);
            when(orderRepo.findById(100L)).thenReturn(Optional.of(order));

            orderService.deleteOrder(100L);

            verify(orderRepo, times(1)).delete(order);
        }
    }
}