package com.borelanjo.despesas.infrastructure.persistence.hibernate.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.borelanjo.despesas.builder.AccountBuilder;
import com.borelanjo.despesas.domain.model.Account;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class AccountRepositoryIntegrationTests {

  @Autowired
  private AccountRepository accountRepository;

  @Before
  public void setUp() {
    this.accountRepository.deleteAll();
  }

  @Test
  public void findAllAccounts() {

    accountRepository.save(new AccountBuilder()
        .withAccountNumber(1)
        .withBalance(5.0)
        .build());

    accountRepository.save(new AccountBuilder()
        .withAccountNumber(2)
        .withBalance(5.0)
        .build());

    List<Account> accounts = accountRepository.findByDeleted(false);
    assertThat(accounts.size(), equalTo(2));
  }

  @Test
  public void createAccount() {

    Account account = new AccountBuilder()
        .withAccountNumber(1)
        .withBalance(5.0)
        .build();

    Account result = this.accountRepository.save(account);

    assertThat(result.getAccountNumber(), equalTo(account.getAccountNumber()));
    assertThat(result.getBalance(), equalTo(account.getBalance()));
  }

  @Test
  public void findAccountNumber() {

    Integer accountNumber = 123456;

    this.accountRepository.save(new AccountBuilder()
        .withAccountNumber(accountNumber)
        .withBalance(5.0)
        .build());

    Account account = this.accountRepository.findOneByAccountNumber(accountNumber);
    assertThat(account.getAccountNumber(), equalTo(accountNumber));
  }

  @Test
  public void updateBalance() {

    Integer accountNumber = 123456;

    Account account = this.accountRepository
        .save(new AccountBuilder()
            .withAccountNumber(accountNumber)
            .withBalance(5.0).build());

    account.setBalance(12.0);
    Account result = this.accountRepository.save(account);
    assertThat(result.getBalance(), equalTo(12.0));
  }

}
