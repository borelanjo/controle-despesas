package com.borelanjo.despesas.builder;

import com.borelanjo.despesas.domain.model.Account;

public class AccountBuilder {

	private Integer accountNumber = 1111;

	private Double balance = 1000.0;

	public AccountBuilder withAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
		return this;
	}
	
	public AccountBuilder withBalance(Double balance) {
		this.balance = balance;
		return this;
	}

	public Account build() {
		return new Account(accountNumber, balance);
	}

}
