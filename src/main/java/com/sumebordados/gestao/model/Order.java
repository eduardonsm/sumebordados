package com.sumebordados.gestao.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Table(name = "ORDERS")
@Entity(name = "ORDERS")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private int customer_id;
    private String model;
    private String fabric;
    private boolean has_cut;
    private int quantity;
    private int chest_customization ;
    private int back_customization ;
    private int sleeve_customization ;
    private float unit_price ;
    private float total_price ;
    private LocalDate  delivery_date ;
    private LocalDate advance_date ;
    private float advance_amount ;
    private float remaining_amount ;
    //private ENUM status order_type NOT NULL;
    private String artwork_url;
}
