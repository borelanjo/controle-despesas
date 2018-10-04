package com.borelanjo.despesas.application.service;

import java.lang.reflect.Field;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.borelanjo.despesas.domain.model.BaseEntity;
import com.borelanjo.despesas.domain.service.BaseService;
import com.borelanjo.despesas.infrastructure.persistence.hibernate.repository.BaseRepository;

@Service
public abstract class BaseServiceImpl<T extends BaseEntity<ID>, ID> implements BaseService<T, ID> {

  @Autowired
  protected BaseRepository<T, ID> repository;

  @Override
  public T create(T entity) {
    return repository.save(entity);
  }

  @Override
  public Optional<T> findById(ID id) {
    return repository.findById(id);
  }

  @Override
  public T update(ID id, T entity) {
    Optional<T> oldEntity = repository.findById(id);

    if (!oldEntity.isPresent()) {
      return null;
    }
    setId(entity, id);
    return repository.save(entity);
  }

  @Override
  public void delete(ID id) {
    Optional<T> optionalEntity = repository.findById(id);

    if (!optionalEntity.isPresent()) {
      return;
    }
    optionalEntity.get().delete();
    repository.save(optionalEntity.get());
  }

  @Override
  public void switchActivation(ID id) {
    Optional<T> optionalEntity = repository.findById(id);

    if (!optionalEntity.isPresent()) {
      return;
    }
    optionalEntity.get().switchActivation();
    ;
    repository.save(optionalEntity.get());

  }

  private void setId(T entity, ID id) {
    Field idField = ReflectionUtils.findField(BaseEntity.class, "id");
    ReflectionUtils.makeAccessible(idField);
    ReflectionUtils.setField(idField, entity, id);

  }

  public abstract BaseRepository<T, ID> getRepository();

}
