package com.sumebordados.gestao.model;
import com.sumebordados.gestao.model.enums.BaseSizeType;
import com.sumebordados.gestao.model.enums.VariantType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ORDER_SIZES")
public class OrderSize {
    @EmbeddedId
    private OrderSizeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "quantity")
    private Integer quantity;

    public BaseSizeType getBaseSize() {
        return id != null ? id.getBase_size() : null;
    }

    public VariantType getVariant() {
        return id != null ? id.getVariant() : null;
    }
}
