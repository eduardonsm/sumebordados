package com.sumebordados.gestao.model;
import com.sumebordados.gestao.model.enums.BaseSizeType;
import com.sumebordados.gestao.model.enums.VariantType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Data
@Entity
@Table(name = "ORDER_SIZES")
@NoArgsConstructor
@AllArgsConstructor
public class OrderSize {
    @EmbeddedId
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private OrderSizeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Order order;

    @Column(name = "quantity")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Integer quantity;

    public BaseSizeType getBaseSize() {
        return id != null ? id.getBase_size() : null;
    }

    public VariantType getVariant() {
        return id != null ? id.getVariant() : null;
    }
}
