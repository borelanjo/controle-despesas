package com.borelanjo.despesas.infrastructure.persistence.hibernate.listener;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;

import com.borelanjo.despesas.domain.model.TransactionHistory;

public class TransactionHistoryListener {

	@PrePersist
	protected void onCreate(TransactionHistory transactionHistory) {
		transactionHistory.setCreated(LocalDateTime.now());
	}

}
