package com.borelanjo.despesas.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "t_account")
public class Account extends BaseEntity<Long> {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false, unique = true)
  private Integer accountNumber;

  @Column(nullable = false)
  private Double balance;

  protected Account() {
  }

  public Account(Integer accountNumber, Double balance) {
    super();
    this.accountNumber = accountNumber;
    this.balance = balance;
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
    return getAccountNumber() + "," + getBalance();
  }

}
