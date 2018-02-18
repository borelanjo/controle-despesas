package com.borelanjo.despesas.infrastructure.persistence.hibernate.listener;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.borelanjo.despesas.domain.model.Account;

public class AccountListener {

	@PrePersist
	protected void onCreate(Account account) {
		account.setCreated(LocalDateTime.now());
		account.setUpdated(LocalDateTime.now());
	}

	@PreUpdate
	protected void onUpdate(Account account) {
		account.setUpdated(LocalDateTime.now());
	}

}
