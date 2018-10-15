package com.borelanjo.despesas.application.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.borelanjo.despesas.domain.enumeration.TransactionType;
import com.borelanjo.despesas.domain.model.Account;
import com.borelanjo.despesas.domain.model.TransactionHistory;
import com.borelanjo.despesas.domain.service.AccountService;
import com.borelanjo.despesas.infrastructure.persistence.hibernate.repository.AccountRepository;
import com.borelanjo.despesas.infrastructure.persistence.hibernate.repository.BaseRepository;
import com.borelanjo.despesas.infrastructure.persistence.hibernate.repository.TransactionHistoryRepository;

@Component("accountService")
@Transactional
public class AccountServiceImpl extends BaseServiceImpl<Account, Long> implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    public Account findByAccount(Integer accountNumber) {
        Account result = accountRepository.findOneByAccountNumber(accountNumber);
        return result;
    }

    public TransactionHistory addTransaction(Long id, TransactionType transactionType, Double value) {
        Account account = findById(id);
        Assert.notNull(account, "Conta não deve ser nula");
        value = fixValue(transactionType, value);
        account.setBalance(account.getBalance() + value);
        String description = transactionType.type() + ": R$ " + value + ". Novo Saldo: R$" + account.getBalance();
        this.accountRepository.save(account);
        TransactionHistory transactionHistory = new TransactionHistory(account, transactionType.type(), description,
                value);
        TransactionHistory result = transactionHistoryRepository.save(transactionHistory);
        return result;
    }

    public Double checkBalance(Long id) {
        Account account = findById(id);
        return account.getBalance();
    }

    public TransactionHistory transfer(Long sourceId, Long destinationId, Double value) {
        TransactionHistory result;
        try {
            Account sourceAccount = findById(sourceId);
            Account destinationAccount = findById(destinationId);
            String sourceDescription = null;
            String destinationDescription = TransactionType.INCREASE.type() + ": Recebido R$ " + value + " da conta "
                    + sourceAccount.getAccountNumber();

            sourceAccount.setBalance(sourceAccount.getBalance() - value);
            destinationAccount.setBalance(destinationAccount.getBalance() + value);

            sourceDescription = String.format("%1$s: Transferido R$ %2$s para a conta %3$s. Novo Saldo: R$%4$s",
                    TransactionType.DECREASE.type(), value, destinationAccount.getAccountNumber(),
                    sourceAccount.getBalance());
            destinationDescription = String.format("%1$s: Recebido R$ %2$s da conta %3$s. Novo Saldo: R$%4$s",
                    TransactionType.INCREASE.type(), value, sourceAccount.getAccountNumber(),
                    destinationAccount.getBalance());

            TransactionHistory sourceTransactionHistory = new TransactionHistory(sourceAccount,
                    TransactionType.DECREASE.type(), sourceDescription, value);
            TransactionHistory destinationTransactionHistory = new TransactionHistory(destinationAccount,
                    TransactionType.INCREASE.type(), destinationDescription, value);

            this.transactionHistoryRepository.save(destinationTransactionHistory);
            result = transactionHistoryRepository.save(sourceTransactionHistory);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Conta não existe!");
        }
        return result;
    }

    public List<TransactionHistory> showHistory(Long id) {
        List<TransactionHistory> transactionHistories = transactionHistoryRepository.findByAccountId(id);
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

    @Override
    public BaseRepository<Account, Long> getRepository() {
        return accountRepository;
    }

}
