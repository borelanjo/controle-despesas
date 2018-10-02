package com.borelanjo.despesas.domain.service;

import java.util.List;

import com.borelanjo.despesas.domain.enumeration.TransactionType;
import com.borelanjo.despesas.domain.model.Account;
import com.borelanjo.despesas.domain.model.TransactionHistory;

public interface AccountService {

    Account createAccount(Integer accountNumber, Double balance);

    Account getAccount(Integer accountNumber);

    TransactionHistory addTransaction(Integer accountNumber, TransactionType type, Double value);

    Double checkBalance(Integer accountNumber);

    TransactionHistory transfer(Integer sourceAccount, Integer destinationAccount, Double value);

    List<TransactionHistory> showHistory(Integer accountNumber);

}
