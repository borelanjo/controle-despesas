package com.borelanjo.despesas.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
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
	
	@Before
	public void setUp() {
		this.accountRepository.deleteAll();
	}

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
		
		Account accountExpected = new AccountBuilder()
				.withAccountNumber(123460)
				.withBalance(5.0)
				.build();
		this.accountRepository.save(accountExpected);

		TransactionHistory transactionHistory = this.serviceImpl.addTransaction(accountExpected.getAccountNumber(), TransactionType.INCREASE,
				5.0);
		assertThat(transactionHistory.getDescription()).isEqualTo("Receita: R$ 5.0. Novo Saldo: R$10.0");
	}

	@Test
	public void transfer() {
		Integer sourceAccountNumber = 123460;
		Integer destinationAccountNumber = 456790;
		
		this.accountRepository.save(new AccountBuilder()
				.withAccountNumber(sourceAccountNumber)
				.withBalance(5.0)
				.build());
		
		this.accountRepository.save(new AccountBuilder()
				.withAccountNumber(destinationAccountNumber)
				.withBalance(5.0)
				.build());

		TransactionHistory transactionHistory = this.serviceImpl.transfer(sourceAccountNumber, destinationAccountNumber,
				5.0);
		assertThat(transactionHistory.getDescription()).isEqualTo("Despesa: Transferido R$ 5.0 para a conta 456790. Novo Saldo: R$0.0");
	}

	@Test
	public void showHistory() {
		Integer sourceAccountNumber = 123460;
		Integer destinationAccountNumber = 456790;
		
		this.accountRepository.save(new AccountBuilder()
				.withAccountNumber(sourceAccountNumber)
				.withBalance(1000.0)
				.build());
		
		this.accountRepository.save(new AccountBuilder()
				.withAccountNumber(destinationAccountNumber)
				.withBalance(5.0)
				.build());
		
		this.serviceImpl.transfer(sourceAccountNumber, destinationAccountNumber, 5.0);
		this.serviceImpl.transfer(sourceAccountNumber, destinationAccountNumber, 10.0);
		this.serviceImpl.transfer(sourceAccountNumber, destinationAccountNumber, 15.0);
		this.serviceImpl.transfer(sourceAccountNumber, destinationAccountNumber, 20.0);
		this.serviceImpl.addTransaction(sourceAccountNumber, TransactionType.INCREASE, 5.0);
		this.serviceImpl.addTransaction(sourceAccountNumber, TransactionType.DECREASE, 20.0);

		List<TransactionHistory> sourceAccountTransactionHistories = this.serviceImpl.showHistory(sourceAccountNumber);
		List<TransactionHistory> destinationTransactionHistories = this.serviceImpl.showHistory(destinationAccountNumber);
		assertThat(sourceAccountTransactionHistories.size()).isEqualTo(6);
		assertThat(destinationTransactionHistories.size()).isEqualTo(4);
	}

}
