package com.example.ebanking_backend.services;

import com.example.ebanking_backend.dtos.*;
import com.example.ebanking_backend.entities.BankAccount;
import com.example.ebanking_backend.entities.CurrentAccount;
import com.example.ebanking_backend.entities.Customer;
import com.example.ebanking_backend.entities.SavingAccount;
import com.example.ebanking_backend.exception.BalanceNotSufficentException;
import com.example.ebanking_backend.exception.BankAccountNotFoundException;
import com.example.ebanking_backend.exception.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerId, double interestRate) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void credit(String accountId, double amount) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
