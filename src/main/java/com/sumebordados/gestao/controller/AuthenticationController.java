package com.sumebordados.gestao.controller;

import com.sumebordados.gestao.dto.auth.AuthenticationDTO;
import com.sumebordados.gestao.dto.auth.LoginResponseDTO;
import com.sumebordados.gestao.dto.employee.EmployeeRequestDTO;
import com.sumebordados.gestao.infra.security.TokenService;
import com.sumebordados.gestao.model.Employee;
import com.sumebordados.gestao.service.EmployeeService;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Employee) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}