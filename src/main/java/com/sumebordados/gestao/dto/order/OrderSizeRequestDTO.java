package com.sumebordados.gestao.dto.order;

import com.sumebordados.gestao.model.enums.BaseSizeType;
import com.sumebordados.gestao.model.enums.VariantType;

public record OrderSizeRequestDTO(
        BaseSizeType base_size,
        VariantType variant,
        Integer quantity
) { }
