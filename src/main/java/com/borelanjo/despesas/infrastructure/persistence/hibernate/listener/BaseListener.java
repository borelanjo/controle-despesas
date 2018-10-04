package com.borelanjo.despesas.infrastructure.persistence.hibernate.listener;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.util.ReflectionUtils;

import com.borelanjo.despesas.domain.model.Account;
import com.borelanjo.despesas.domain.model.BaseEntity;

public class BaseListener<ID> {

  @PrePersist
  protected void onCreate(BaseEntity<ID> entity) {
    entity.setCreated(LocalDateTime.now());
    entity.setUpdated(LocalDateTime.now());
    entity.setActivated(true);
    setDeleted(entity);
  }

  @PreUpdate
  protected void onUpdate(Account account) {
    account.setUpdated(LocalDateTime.now());
  }
  
  private void setDeleted(BaseEntity<ID> entity) {
    Field deletedField = ReflectionUtils.findField(BaseEntity.class, "deleted");
    ReflectionUtils.makeAccessible(deletedField);
    ReflectionUtils.setField(deletedField, entity, false);
    
  }

}
