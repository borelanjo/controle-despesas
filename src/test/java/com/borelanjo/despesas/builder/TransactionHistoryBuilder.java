package com.borelanjo.despesas.builder;

import com.borelanjo.despesas.domain.enumeration.TransactionType;
import com.borelanjo.despesas.domain.model.Account;
import com.borelanjo.despesas.domain.model.TransactionHistory;

public class TransactionHistoryBuilder {

	private Account account;

	private String type;

	private String description;

	private Double value;

	public AccountBuilder withAccount(AccountBuilder accountBuilder) {
		return accountBuilder;
	}

	public TransactionHistoryBuilder withType(TransactionType type) {
		this.type = type.name();
		return this;
	}

	public TransactionHistoryBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	public TransactionHistoryBuilder withValue(Double value) {
		this.value = value;
		return this;
	}
	
	public TransactionHistory build() {
		return new TransactionHistory(account, type, description, value);
	}

}
