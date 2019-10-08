package com.borelanjo.despesas.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T, ID> {
  
  T create(T entity);
  
  T findById(ID id);
  
  Page<T> findAll(Pageable pageable);
  
  List<T> findAll();
  
  T update(ID id, T entity);
  
  void delete(ID id);
  
  void switchActivation(ID id);

}
