package com.borelanjo.despesas.infrastructure.persistence.hibernate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface BaseRepository<T,ID> extends Repository<T, ID>{
  
  List<T> findByDeleted(Boolean deleted);
  
  T save(T entity);
  
  Optional<T> findByIdAndDeleted(ID id, Boolean deleted);

  void deleteAll();

}
