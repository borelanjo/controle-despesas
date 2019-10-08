package com.borelanjo.despesas.infrastructure.persistence.hibernate.repository;

import com.borelanjo.despesas.domain.model.Account;

public interface AccountRepository extends BaseRepository<Account, Long> {

  Account findOneByAccountNumber(Integer accountNumber);

}
