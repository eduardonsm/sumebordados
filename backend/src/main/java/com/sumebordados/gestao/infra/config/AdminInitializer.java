package com.sumebordados.gestao.infra.config;

import com.sumebordados.gestao.model.Employee;
import com.sumebordados.gestao.model.enums.EmployeeRole;
import com.sumebordados.gestao.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class AdminInitializer {

    private final EmployeeRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner createAdmin() {
        return args -> {

            if (repository.findByUsername("eduardonsm").isEmpty()) {

                Employee admin = new Employee(
                    "Eduardo Severo",
                    "eduardonsm",
                    passwordEncoder.encode("bordados"),
                    EmployeeRole.ADMIN
                );

                repository.save(admin);

                System.out.println("ADMIN criado automaticamente.");
            }
        };
    }
}