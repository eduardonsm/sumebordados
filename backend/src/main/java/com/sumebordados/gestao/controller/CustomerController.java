package com.sumebordados.gestao.controller;

import com.sumebordados.gestao.dto.customer.CustomerRequestDTO;
import com.sumebordados.gestao.dto.customer.CustomerResponseDTO;
import com.sumebordados.gestao.dto.customer.DeleteCustomerResponseDTO;
import com.sumebordados.gestao.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @RequestBody CustomerRequestDTO dto) {

        CustomerResponseDTO created = customerService.createCustomer(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable("id") Long id,
            @RequestBody CustomerRequestDTO dto) {

        CustomerResponseDTO updated = customerService.updateCustomer(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteCustomerResponseDTO> deleteCustomer(
            @PathVariable("id") Long id) {

        DeleteCustomerResponseDTO deleted = customerService.deleteCustomer(id);
        return ResponseEntity.ok(deleted);
    }
}
