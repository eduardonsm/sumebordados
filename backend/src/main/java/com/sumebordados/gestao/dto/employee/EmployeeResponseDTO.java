package com.sumebordados.gestao.dto.employee;

import com.sumebordados.gestao.model.enums.EmployeeRole;

public record EmployeeResponseDTO(
        Long id,
        String username,
        String senha,
        EmployeeRole role
) {
}
