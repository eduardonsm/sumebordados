package com.sumebordados.gestao.model;

import com.sumebordados.gestao.model.enums.EmployeeRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity(name = "EMPLOYEES")
@Table(name = "EMPLOYEES")
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    @ToString.Exclude
    private String senha;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeRole role;

    public Employee(String nome, String username, String senha, EmployeeRole role) {
        this.nome = nome;
        this.username = username;
        this.senha = senha;
        this.role = role;
    }
}
