package com.borelanjo.despesas.presentation.dto.account;

public class AccountRequestTO {
    
    private Integer accountNumber;

    private Double balance;
    
    public AccountRequestTO() {
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountRequestTO [accountNumber=" + accountNumber + ", balance=" + balance + "]";
    }
    
    

}
