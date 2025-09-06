package com.sumebordados.gestao.repository;

import com.sumebordados.gestao.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long > {
}
