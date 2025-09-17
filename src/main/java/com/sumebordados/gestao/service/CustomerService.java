package com.sumebordados.gestao.service;

import com.sumebordados.gestao.dto.customer.CustomerRequestDTO;
import com.sumebordados.gestao.dto.customer.CustomerResponseDTO;
import com.sumebordados.gestao.dto.customer.DeleteCustomerResponseDTO;
import com.sumebordados.gestao.exception.CustomerNotFoundException;
import com.sumebordados.gestao.model.Customer;
import com.sumebordados.gestao.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepo;

    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto){

        Customer customer = new Customer(
                dto.nome(),
                dto.telefone(),
                dto.endereco()
        );

        Customer saved = customerRepo.save(customer);

        return new CustomerResponseDTO(saved.getId(), saved.getNome(), saved.getTelefone(), saved.getEndereco());
    }
    @Transactional
    public DeleteCustomerResponseDTO deleteCustomer(Long id){

        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        customerRepo.delete(customer);

        return new DeleteCustomerResponseDTO(customer.getId(), customer.getNome());
    }
    @Transactional
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO dto){

        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        customer.setNome(dto.nome());
        customer.setTelefone(dto.telefone());
        customer.setEndereco(dto.endereco());

        Customer saved = customerRepo.save(customer);

        return new CustomerResponseDTO(saved.getId(), saved.getNome(), saved.getTelefone(), saved.getEndereco());
    }

}
