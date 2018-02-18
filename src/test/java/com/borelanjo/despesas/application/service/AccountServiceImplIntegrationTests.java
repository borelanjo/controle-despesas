package com.borelanjo.despesas.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.borelanjo.despesas.builder.AccountBuilder;
import com.borelanjo.despesas.domain.enumeration.TransactionType;
import com.borelanjo.despesas.domain.model.Account;
import com.borelanjo.despesas.domain.model.TransactionHistory;
import com.borelanjo.despesas.infrastructure.persistence.hibernate.repository.AccountRepository;
import com.borelanjo.despesas.infrastructure.persistence.hibernate.repository.TransactionHistoryRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class AccountServiceImplIntegrationTests {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	TransactionHistoryRepository transactionHistoryRepository;

	@Autowired
	AccountServiceImpl serviceImpl;

	@Test
	public void createAccount() {

		Account accountExpected = new AccountBuilder()
				.withAccountNumber(1111)
				.withBalance(10000.0)
				.build();

		Account result = this.serviceImpl.createAccount(accountExpected.getAccountNumber(), accountExpected.getBalance());
		
		assertThat(result.getBalance()).isEqualTo(accountExpected.getBalance());
		assertThat(result.getAccountNumber()).isEqualTo(accountExpected.getAccountNumber());
	}

	@Test
	public void addTransaction() {

		Integer accountNumber = 123460;

		TransactionHistory transactionHistory = this.serviceImpl.addTransaction(accountNumber, TransactionType.INCREASE,
				5.0);
		assertThat(transactionHistory.getDescription()).isEqualTo("Receita: R$ 5.0");
	}

	@Test
	public void transfer() {
		Integer sourceAccountNumber = 123460;
		Integer destinationAccountNumber = 456790;

		TransactionHistory transactionHistory = this.serviceImpl.transfer(sourceAccountNumber, destinationAccountNumber,
				5.0);
		assertThat(transactionHistory.getDescription()).isEqualTo("Despesa: Transferido R$ 5.0 para a conta 456799");
	}

	@Test
	public void showHistory() {

		Integer sourceAccountNumber = 123456;

		List<TransactionHistory> transactionHistories = this.serviceImpl.showHistory(sourceAccountNumber);
		assertThat(transactionHistories.size()).isEqualTo(10);
	}

}
