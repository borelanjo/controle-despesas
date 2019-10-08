package com.borelanjo.despesas.infrastructure.persistence.hibernate.repository;

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
import com.borelanjo.despesas.builder.TransactionHistoryBuilder;
import com.borelanjo.despesas.domain.enumeration.TransactionType;
import com.borelanjo.despesas.domain.model.Account;
import com.borelanjo.despesas.domain.model.TransactionHistory;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransactionHistoryRepositoryIntegrationTests {

    @Autowired
    TransactionHistoryRepository repository;

    @Autowired
    AccountRepository accountRepository;

    @Before
    public void setUp() {
        repository.deleteAll();
        this.accountRepository.deleteAll();
    }

    @Test
    public void createTransactionHistory() {

        Account accountExpected = new AccountBuilder()
                .withAccountNumber(123460)
                .withBalance(5.0).build();
        
        accountExpected = this.accountRepository.save(accountExpected);

        TransactionHistory transactionHistory = new TransactionHistoryBuilder()
                .withAccount(accountExpected)
                .withType(TransactionType.DECREASE).withDescription("Saque conta corrente no valor de R$ 25,00")
                .withValue(-25.0).build();

        TransactionHistory result = this.repository.save(transactionHistory);
        assertThat(result.getValue()).isEqualTo(-25.0);
    }

    @Test
    public void findAllTransactionHistory() {
        Account accountExpected = this.accountRepository
                .save(new AccountBuilder()
                        .withAccountNumber(123460)
                        .withBalance(5.0)
                        .build());

        repository.save(new TransactionHistoryBuilder()
                .withAccount(accountExpected)
                .withType(TransactionType.DECREASE)
                .withDescription("Saque conta corrente no valor de R$ 25,00")
                .withValue(-25.0)
                .build());

        List<TransactionHistory> transactionHistories = this.repository.findAll();
        assertThat(transactionHistories.size()).isGreaterThan(0);
    }

    @Test
    public void findAllTransactionHistoryByAccount() {
        Account accountExpected = this.accountRepository
                .save(new AccountBuilder()
                        .withAccountNumber(123460)
                        .withBalance(5.0)
                        .build());

        repository.save(new TransactionHistoryBuilder()
                .withAccount(accountExpected)
                .withType(TransactionType.DECREASE)
                .withDescription("Saque conta corrente no valor de R$ 25,00")
                .withValue(-25.0).build());

        repository.save(new TransactionHistoryBuilder()
                .withAccount(accountExpected)
                .withType(TransactionType.DECREASE)
                .withDescription("Saque conta corrente no valor de R$ 25,00")
                .withValue(-25.0).build());

        List<TransactionHistory> transactionHistory = this.repository.findByAccountId(accountExpected.getId());
        assertThat(transactionHistory.size()).isGreaterThan(1);
    }

}
