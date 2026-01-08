package com.sumebordados.gestao.model;
import com.sumebordados.gestao.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.ConstructorParameters;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDERS")
@Entity(name = "ORDERS")
public class Order {

    public Order(Customer customer, String model, String fabric, boolean has_cut, Integer quantity, Integer chest_customization, Integer back_customization, Integer sleeve_customization, Float unit_price, Float total_price, LocalDate delivery_date, LocalDate advance_date, Float advance_amount, Float remaining_amount, OrderStatus status, String artwork_url, Set<String> colors) {
        this.customer = customer;
        this.model = model;
        this.fabric = fabric;
        this.has_cut = has_cut;
        this.quantity = quantity;
        this.chest_customization = chest_customization;
        this.back_customization = back_customization;
        this.sleeve_customization = sleeve_customization;
        this.unit_price = unit_price;
        this.total_price = total_price;
        this.delivery_date = delivery_date;
        this.advance_date = advance_date;
        this.advance_amount = advance_amount;
        this.remaining_amount = remaining_amount;
        this.status = status;
        this.artwork_url = artwork_url;
        this.colors = colors;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
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
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<OrderSize> sizes = new HashSet<>();


}
