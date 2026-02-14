package com.sumebordados.gestao.dto.employee;

import com.sumebordados.gestao.model.enums.EmployeeRole;

public record EmployeeRequestDTO(
        String nome,
        String username,
        String senha,
        EmployeeRole role
) {
    public EmployeeRequestDTO(String nome,
                              String username,
                              String senha,
                              EmployeeRole role) {
        this.nome = nome;
        this.username = username;
        this.senha = senha;
        this.role = role;
    }
    public EmployeeRequestDTO(String nome,
                              String username,
                              String senha
                              ) {
        this(nome, username, senha, EmployeeRole.EMPLOYEE);
    }
}
