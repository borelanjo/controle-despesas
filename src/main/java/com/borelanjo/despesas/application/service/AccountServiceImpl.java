package com.borelanjo.despesas.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.borelanjo.despesas.domain.enumeration.TransactionType;
import com.borelanjo.despesas.domain.model.Account;
import com.borelanjo.despesas.domain.model.TransactionHistory;
import com.borelanjo.despesas.domain.service.AccountService;
import com.borelanjo.despesas.infrastructure.persistence.hibernate.repository.AccountRepository;
import com.borelanjo.despesas.infrastructure.persistence.hibernate.repository.TransactionHistoryRepository;

@Component("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionHistoryRepository transactionHistoryRepository;



	public Account createAccount(Integer accountNumber, Double balance) {
		Account account = new Account(accountNumber, balance);
		Account result = accountRepository.save(account);
		return result;
	}
	
	public Account getAccount(Integer accountNumber) {
		Account result = accountRepository.findOneByAccountNumber(accountNumber);
		return result;
	}

	public TransactionHistory addTransaction(Integer accountNumber, TransactionType transactionType, Double value) {
		Account account = this.accountRepository.findOneByAccountNumber(accountNumber);
		Assert.notNull(account, "Conta não deve ser nula");
		value = fixValue(transactionType, value);
		account.setBalance(account.getBalance()+ value);
		String description = transactionType.type()+ ": R$ "+value+". Novo Saldo: R$"+account.getBalance();
		this.accountRepository.save(account);
		TransactionHistory transactionHistory = new TransactionHistory(account, transactionType.type(), description, value);
		TransactionHistory result = transactionHistoryRepository.save(transactionHistory);
		return result;
	}

	public Double checkBalance(Integer accountNumber) {
		Account account = this.accountRepository.findOneByAccountNumber(accountNumber);
		return account.getBalance();
	}

	public TransactionHistory transfer(Integer sourceAccountNumber, Integer destinationAccountNumber, Double value) {
		Account sourceAccount = this.accountRepository.findOneByAccountNumber(sourceAccountNumber);
		Assert.notNull(sourceAccount, "Conta de origem não pode ser nula");
		Account destinationAccount = this.accountRepository.findOneByAccountNumber(destinationAccountNumber);
		Assert.notNull(sourceAccount, "Conta de destino não pode ser nula");
		String sourceDescription = null;
		String destinationDescription = TransactionType.INCREASE.type()+ ": Recebido R$ "+value+" da conta "+sourceAccountNumber;
		
		sourceAccount.setBalance(sourceAccount.getBalance()-value);
		destinationAccount.setBalance(destinationAccount.getBalance()+value);
		
		sourceDescription = TransactionType.DECREASE.type()+ ": Transferido R$ "+value+" para a conta "+destinationAccountNumber+". Novo Saldo: R$"+sourceAccount.getBalance();
		destinationDescription = TransactionType.INCREASE.type()+ ": Recebido R$ "+value+" da conta "+sourceAccountNumber+". Novo Saldo: R$"+destinationAccount.getBalance();
		
		TransactionHistory sourceTransactionHistory = new TransactionHistory(sourceAccount, TransactionType.DECREASE.type(), sourceDescription, value);
		TransactionHistory destinationTransactionHistory = new TransactionHistory(destinationAccount, TransactionType.INCREASE.type(), destinationDescription, value);
		
		this.transactionHistoryRepository.save(destinationTransactionHistory);
		TransactionHistory result = transactionHistoryRepository.save(sourceTransactionHistory);
		return result;
	}

	public List<TransactionHistory> showHistory(Integer accountNumber) {
		Account account = this.accountRepository.findOneByAccountNumber(accountNumber);
		List<TransactionHistory> transactionHistories = transactionHistoryRepository.findByAccount(account);
		return transactionHistories;
	}

	private Double fixValue(TransactionType type, Double value) {
		if (type == TransactionType.DECREASE) {
			if (value > 0) {
				value *= -1;
			}
		} else if (type == TransactionType.INCREASE) {
			if (value < 0) {
				value *= -1;
			}
		}
		return value;

	}
}
