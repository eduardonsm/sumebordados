package com.sumebordados.gestao.dto.order;

import com.sumebordados.gestao.model.enums.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.Set;

public record OrderRequestDTO(
        @NotNull
        Long customerId,
        String model,
        String fabric,
        boolean has_cut,
        @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
        Integer quantity,
        Integer chest_customization ,
        Integer back_customization ,
        Integer sleeve_customization,
        @PositiveOrZero(message = "O preço unitário não pode ser negativo")
        Float unit_price ,
        @PositiveOrZero(message = "O preço total não pode ser negativo")
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
