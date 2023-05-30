package com.example.ebanking_backend.dtos;


import com.example.ebanking_backend.enums.AccountSatus;
import lombok.Data;

import java.util.Date;


@Data
public class CurrentBankAccountDTO extends BankAccountDTO{
    private String id;
    private double balance;
    private Date createAt;
    private AccountSatus status;
    private CustomerDTO customerDTO;
    private double overDraft;

}
