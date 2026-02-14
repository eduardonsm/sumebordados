package com.sumebordados.gestao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import com.sumebordados.gestao.model.enums.VariantType;
import com.sumebordados.gestao.model.enums.BaseSizeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class OrderSizeId implements Serializable {

    public OrderSizeId(BaseSizeType base_size, VariantType variant) {
        this.base_size = base_size;
        this.variant = variant;
    }
    @Column(name = "order_id")
    private Long order_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "base_size")
    private BaseSizeType base_size;

    @Enumerated(EnumType.STRING)
    @Column(name = "variant")
    private VariantType variant;
}