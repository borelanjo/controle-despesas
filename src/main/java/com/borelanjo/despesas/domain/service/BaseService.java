package com.borelanjo.despesas.domain.service;

import java.util.Optional;

public interface BaseService<T, ID> {
  
  T create(T entity);
  
  Optional<T> findById(ID id);
  
  T update(ID id, T entity);
  
  void delete(ID id);
  
  void switchActivation(ID id);

}
