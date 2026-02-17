package com.sumebordados.gestao.dto.employee;

import com.sumebordados.gestao.model.enums.EmployeeRole;
import jakarta.validation.constraints.NotNull;

public record EmployeeRequestDTO(
        @NotNull
        String nome,
        @NotNull
        String username,
        @NotNull
        String senha,
        @NotNull
        EmployeeRole role
) {
    public EmployeeRequestDTO(String nome,
            String username,
            String senha) {
        this(nome, username, senha, EmployeeRole.EMPLOYEE);
    }
}
