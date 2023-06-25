package com.example.ebanking_backend.repositories;

import com.example.ebanking_backend.entities.BankAccount;
import com.example.ebanking_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    List<BankAccount> findByCustomer(Optional<Customer> customer);
}
