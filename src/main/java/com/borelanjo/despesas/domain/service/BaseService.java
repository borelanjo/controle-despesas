package com.borelanjo.despesas.domain.service;

public interface BaseService<T, ID> {
  
  T create(T entity);
  
  T findById(ID id);
  
  T update(ID id, T entity);
  
  void delete(ID id);
  
  void switchActivation(ID id);

}
