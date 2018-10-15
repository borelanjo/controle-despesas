package com.borelanjo.despesas.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.borelanjo.despesas.application.service.exception.EntityIsNotPresent;
import com.borelanjo.despesas.builder.AccountBuilder;
import com.borelanjo.despesas.domain.enumeration.TransactionType;
import com.borelanjo.despesas.domain.model.Account;
import com.borelanjo.despesas.domain.model.TransactionHistory;
import com.borelanjo.despesas.infrastructure.persistence.hibernate.repository.AccountRepository;
import com.borelanjo.despesas.infrastructure.persistence.hibernate.repository.TransactionHistoryRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class AccountServiceImplTest {
    
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    AccountServiceImpl serviceImpl;
    
    private Integer accountNumber = 1111;

    @Before
    public void setUp() {
        this.accountRepository.deleteAll();
    }

    @Test
    public void testCreate() throws Exception {

        Account accountExpected = generateAccount();

        Account result = this.serviceImpl.create(accountExpected);

        assertThat(result.getBalance()).isEqualTo(accountExpected.getBalance());
        assertThat(result.getAccountNumber()).isEqualTo(accountExpected.getAccountNumber());
    }

    @Test
    public void testFindById() throws Exception {
        Account accountExpected = this.serviceImpl.create(generateAccount());
        Long accountId = accountExpected.getId();
        
        Account result = serviceImpl.findById(accountId);
        
        assertThat(result.getBalance()).isEqualTo(accountExpected.getBalance());
        assertThat(result.getAccountNumber()).isEqualTo(accountExpected.getAccountNumber());
    }
    
    @Test
    public void testFindAll() throws Exception {
        serviceImpl.create(generateAccount());
        serviceImpl.create(generateAccount());
        Long accountId = serviceImpl.create(generateAccount()).getId();
        
        serviceImpl.delete(accountId);
        
        List<Account> list = serviceImpl.findAll();
        
        assertThat(2).isEqualTo(list.size());
    }
    
    @Test
    public void testFindAllPage() throws Exception {
        Pageable pageable = PageRequest.of(0, 1);
        
        serviceImpl.create(generateAccount());
        serviceImpl.create(generateAccount());
        Long accountId = serviceImpl.create(generateAccount()).getId();
        
        serviceImpl.delete(accountId);
        
        Page<Account> pages = serviceImpl.findAll(pageable);
        
        assertThat(2).isEqualTo(pages.getTotalPages());
        assertThat(1).isEqualTo(pages.getNumberOfElements());
        assertThat(2).isEqualTo(pages.getTotalElements());
    }

    @Test
    public void testUpdate() throws Exception {
        Account accountExpected = this.serviceImpl.create(generateAccount());
        accountExpected.setBalance(5.0);
        Long accountId = accountExpected.getId();
        Account result = serviceImpl.update(accountId, accountExpected);
        
        assertThat(result.getBalance()).isEqualTo(accountExpected.getBalance());
        assertThat(result.getAccountNumber()).isEqualTo(accountExpected.getAccountNumber());
    }

    @Test(expected=EntityIsNotPresent.class)
    public void testDelete() throws Exception {
        Account accountExpected = this.serviceImpl.create(generateAccount());
        Long accountId = accountExpected.getId();
        
        serviceImpl.delete(accountId);
        serviceImpl.findById(accountId);
    }

    @Test
    public void testSwitchActivation() throws Exception {
        Account accountExpected = this.serviceImpl.create(generateAccount());
        Long accountId = accountExpected.getId();
        serviceImpl.switchActivation(accountId);
        
        Account result = serviceImpl.findById(accountId);
        
        assertThat(result.getBalance()).isEqualTo(accountExpected.getBalance());
        assertThat(result.getAccountNumber()).isEqualTo(accountExpected.getAccountNumber());
        assertThat(result.getActivated()).isEqualTo(false);
    }
    
    @Test
    public void addTransaction() {

        Account accountExpected = this.accountRepository.save(new AccountBuilder()
                .withAccountNumber(123460)
                .withBalance(5.0)
                .build());

        TransactionHistory transactionHistory = this.serviceImpl.addTransaction(accountExpected.getId(),
                TransactionType.INCREASE, 5.0);
        assertThat(transactionHistory.getDescription()).isEqualTo("Receita: R$ 5.0. Novo Saldo: R$10.0");
    }

    @Test
    public void transfer() {
        Integer sourceAccountNumber = 123460;
        Integer destinationAccountNumber = 456790;

        Account sourceAccount = this.accountRepository
                .save(new AccountBuilder()
                        .withAccountNumber(sourceAccountNumber)
                        .withBalance(5.0)
                        .build());

        Account destinationAccount = this.accountRepository
                .save(new AccountBuilder()
                        .withAccountNumber(destinationAccountNumber)
                        .withBalance(5.0)
                        .build());

        TransactionHistory transactionHistory = this.serviceImpl.transfer(sourceAccount.getId(), destinationAccount.getId(),
                5.0);
        assertThat(transactionHistory.getDescription())
                .isEqualTo("Despesa: Transferido R$ 5.0 para a conta 456790. Novo Saldo: R$0.0");
    }

    @Test
    public void showHistory() {
        Integer sourceAccountNumber = 123460;
        Integer destinationAccountNumber = 456790;

        Long sourceAccountId = this.accountRepository
                .save(new AccountBuilder()
                        .withAccountNumber(sourceAccountNumber)
                        .withBalance(1000.0)
                        .build()).getId();

        Long destinationAccountId = this.accountRepository
                .save(new AccountBuilder()
                        .withAccountNumber(destinationAccountNumber)
                        .withBalance(5.0)
                        .build()).getId();

        this.serviceImpl.transfer(sourceAccountId, destinationAccountId, 5.0);
        this.serviceImpl.transfer(sourceAccountId, destinationAccountId, 10.0);
        this.serviceImpl.transfer(sourceAccountId, destinationAccountId, 15.0);
        this.serviceImpl.transfer(sourceAccountId, destinationAccountId, 20.0);
        this.serviceImpl.addTransaction(sourceAccountId, TransactionType.INCREASE, 5.0);
        this.serviceImpl.addTransaction(sourceAccountId, TransactionType.DECREASE, 20.0);

        List<TransactionHistory> sourceAccountTransactionHistories = this.serviceImpl.showHistory(sourceAccountId);
        List<TransactionHistory> destinationTransactionHistories = this.serviceImpl
                .showHistory(destinationAccountId);
        assertThat(sourceAccountTransactionHistories.size()).isEqualTo(6);
        assertThat(destinationTransactionHistories.size()).isEqualTo(4);
    }
    
    private Account generateAccount() {
        Account accountExpected = new AccountBuilder()
                .withAccountNumber(accountNumber++)
                .withBalance(10000.0)
                .build();
        return accountExpected;
    }

}
