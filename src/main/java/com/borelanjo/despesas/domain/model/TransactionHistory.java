package com.borelanjo.despesas.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.borelanjo.despesas.infrastructure.persistence.hibernate.listener.TransactionHistoryListener;

@Entity(name = "t_transaction_history")
@EntityListeners(TransactionHistoryListener.class)
public class TransactionHistory extends BaseEntity<Long>{

    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_account")
    private Account account;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double value;

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

}
