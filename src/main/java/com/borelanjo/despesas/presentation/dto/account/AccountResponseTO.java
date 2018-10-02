package com.borelanjo.despesas.presentation.dto.account;

public class AccountResponseTO {

    private Long id;

    private Integer accountNumber;

    private Double balance;
    
    public AccountResponseTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "AccountResponseTO [id=" + id + ", accountNumber=" + accountNumber + ", balance=" + balance + "]";
    }
    
}
