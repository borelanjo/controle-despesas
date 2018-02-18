package com.borelanjo.despesas.infrastructure.persistence.hibernate.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.borelanjo.despesas.domain.model.Account;
import com.borelanjo.despesas.domain.model.TransactionHistory;

public interface TransactionHistoryRepository extends Repository<TransactionHistory, Long>{
	
	List<TransactionHistory> findAll();
	
	List<TransactionHistory> findByAccount(Account account);
	
	TransactionHistory save(TransactionHistory transactionHistory);

}
