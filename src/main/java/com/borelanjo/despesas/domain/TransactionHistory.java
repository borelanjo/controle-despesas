package com.borelanjo.despesas.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@Entity(name = "t_transaction_history")
public class TransactionHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_account")
	private Account account;

	@Column(nullable = false)
	private String type;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Double value;

	@Column(name = "dt_created", nullable = false)
	private Date created;

	protected TransactionHistory() {
	}

	public TransactionHistory(Account account, String type, String description, Double value) {
		this.account = account;
		this.type = type;
		this.description = description;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getCreated() {
		return created;
	}

	@PrePersist
	protected void onCreate() {
		created = new Date();
	}

}
