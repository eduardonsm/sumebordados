package com.sumebordados.gestao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class User {

    @Id
    @JsonProperty("Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
}
