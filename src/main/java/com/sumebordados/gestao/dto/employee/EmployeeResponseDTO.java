package com.sumebordados.gestao.dto.employee;

public record EmployeeResponseDTO(
        saved.getId(), saved.getNome(), saved.getUsername(), saved.getRole()
) {
}
