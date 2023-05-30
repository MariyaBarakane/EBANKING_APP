package com.example.ebanking_backend;

import com.example.ebanking_backend.dtos.BankAccountDTO;
import com.example.ebanking_backend.dtos.CurrentBankAccountDTO;
import com.example.ebanking_backend.dtos.CustomerDTO;
import com.example.ebanking_backend.dtos.SavingBankAccountDTO;
import com.example.ebanking_backend.entities.*;
import com.example.ebanking_backend.enums.AccountSatus;
import com.example.ebanking_backend.enums.OperationType;
import com.example.ebanking_backend.exception.BalanceNotSufficentException;
import com.example.ebanking_backend.exception.BankAccountNotFoundException;
import com.example.ebanking_backend.exception.CustomerNotFoundException;
import com.example.ebanking_backend.repositories.AccountOperationRepository;
import com.example.ebanking_backend.repositories.BankAccountRepository;
import com.example.ebanking_backend.repositories.CustomerRepository;
import com.example.ebanking_backend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Hassan","Mohamed","Imane").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer ->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,customer.getId(),9000);
                    bankAccountService.saveSavingBankAccount(Math.random()*90000,customer.getId(),5.5);

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts =bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                String accountId;
                for (int i = 0; i < 10; i++) {
                    if (bankAccount instanceof SavingBankAccountDTO) {
                        accountId=((SavingBankAccountDTO) bankAccount).getId();
                    }
                    else {
                        accountId=((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*12000);
                    try {
                        bankAccountService.debit(accountId,1000+Math.random()*9000);
                    } catch (BalanceNotSufficentException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        };
    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return  args -> {
            Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                Customer customer =new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreateAt(new Date());
                currentAccount.setStatus(AccountSatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreateAt(new Date());
                savingAccount.setStatus(AccountSatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i = 0;i<10; i++){
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }

            });
        };
    }
}
