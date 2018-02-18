package com.borelanjo.despesas.infrastructure.persistence.hibernate.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.borelanjo.despesas.domain.model.Account;

public interface AccountRepository extends Repository<Account, Long>{
	
	List<Account> findAll();
	
	Account findOneByAccountNumber(Integer accountNumber);
	
	
	Account save(Account account);
	

}
