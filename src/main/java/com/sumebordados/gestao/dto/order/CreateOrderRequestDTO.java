package com.sumebordados.gestao.dto.order;

import com.sumebordados.gestao.model.enums.OrderStatus;

import java.time.LocalDate;
import java.util.Set;

public record CreateOrderRequestDTO(
        Long customerId,
        String model,
        String fabric,
        boolean has_cut,
        Integer quantity,
        Integer chest_customization ,
        Integer back_customization ,
        Integer sleeve_customization,
        Float unit_price ,
        Float total_price ,
        LocalDate delivery_date ,
        LocalDate advance_date ,
        Float advance_amount ,
        Float remaining_amount,
        OrderStatus status,
        String artwork_url,
        Set<String> colors,
        Set<OrderSizeRequestDTO> sizes
) {}
