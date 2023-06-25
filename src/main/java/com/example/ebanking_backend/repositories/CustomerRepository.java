package com.example.ebanking_backend.repositories;

import com.example.ebanking_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findByNameContains(String keyword);
}
