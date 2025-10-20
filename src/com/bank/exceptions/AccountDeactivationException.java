package com.bank.exceptions;

import java.math.BigDecimal;

public class AccountDeactivationException extends Exception{
    public AccountDeactivationException(String message){
        super(message);
    }

    public AccountDeactivationException(String accountNumber, BigDecimal balance){
        super(String.format("Can not deactivate account %s. Balance: %s", accountNumber, balance.toString()));
    }
}
