package com.sumebordados.gestao.repository;

import com.sumebordados.gestao.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    UserDetails findByUsername(String username);
}