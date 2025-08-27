package com.sumebordados.gestao.model;
import com.sumebordados.gestao.model.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDate;


@Table(name = "ORDERS")
@Entity(name = "ORDERS")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer customer_id;
    private String model;
    private String fabric;
    private boolean has_cut;
    private Integer quantity;
    private Integer chest_customization ;
    private Integer back_customization ;
    private Integer sleeve_customization ;
    private Float unit_price ;
    private Float total_price ;
    private LocalDate  delivery_date ;
    private LocalDate advance_date ;
    private Float advance_amount ;
    private Float remaining_amount ;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "order_type")
    private OrderStatus status;
    private String artwork_url;
}
