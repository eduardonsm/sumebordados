package com.sumebordados.gestao.service;

import com.sumebordados.gestao.dto.customer.CustomerRequestDTO;
import com.sumebordados.gestao.dto.customer.CustomerResponseDTO;
import com.sumebordados.gestao.dto.employee.EmployeeRequestDTO;
import com.sumebordados.gestao.dto.employee.EmployeeResponseDTO;
import com.sumebordados.gestao.model.Customer;
import com.sumebordados.gestao.model.Employee;
import com.sumebordados.gestao.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepo;
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto){

        Employee employee = new Employee(
                dto.nome(),
                dto.username(),
                dto.senha(),
                dto.role()
        );

        Employee saved = employeeRepo.save(employee);

        return new EmployeeResponseDTO(saved.getId(), saved.getNome(), saved.getUsername(), saved.getRole());
    }

}
