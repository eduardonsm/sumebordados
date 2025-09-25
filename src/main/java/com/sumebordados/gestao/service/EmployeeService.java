package com.sumebordados.gestao.service;
import com.sumebordados.gestao.dto.employee.EmployeeRequestDTO;
import com.sumebordados.gestao.dto.employee.EmployeeResponseDTO;
import com.sumebordados.gestao.exception.EmployeeNotFoundException;
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
    @Transactional
    public void deleteEmployee(Long id){
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employeeRepo.delete(employee);
    }
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto){
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employee.setNome(dto.nome());
        employee.setUsername(dto.username());
        employee.setSenha(dto.senha());
        employee.setRole(dto.role());

        Employee saved = employeeRepo.save(employee);

        return new EmployeeResponseDTO(saved.getId(), saved.getNome(), saved.getUsername(), saved.getRole());
    }

}
