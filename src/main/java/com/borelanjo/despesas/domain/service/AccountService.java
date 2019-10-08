package com.borelanjo.despesas.domain.service;

import java.util.List;

import com.borelanjo.despesas.domain.enumeration.TransactionType;
import com.borelanjo.despesas.domain.model.Account;
import com.borelanjo.despesas.domain.model.TransactionHistory;

public interface AccountService extends BaseService<Account, Long>{

    Account findByAccount(Integer accountNumber);

    TransactionHistory addTransaction(Long id, TransactionType type, Double value);

    Double checkBalance(Long id);

    TransactionHistory transfer(Long souceId, Long destinationId, Double value);

    List<TransactionHistory> showHistory(Long id);

}
