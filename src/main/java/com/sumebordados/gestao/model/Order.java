package com.sumebordados.gestao.model;
import com.sumebordados.gestao.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @ElementCollection
    @CollectionTable(
            name = "ORDER_COLORS", // Nome da tabela que armazenará a coleção
            joinColumns = @JoinColumn(name = "order_id") // Coluna que faz a ligação com a tabela ORDERS
    )
    @Column(name = "color", nullable = false) // Nome da coluna que armazenará as cores
    private Set<String> colors = new HashSet<>(); // Não permite cor duplicada

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )
    private Set<OrderSize> sizes = new HashSet<>();


}
