package com.borelanjo.despesas.application.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.borelanjo.despesas.application.service.exception.EntityIsNotPresent;
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
    public Page<T> findAll(Pageable pageable) {
        return repository.findByDeleted(false, pageable);
    }
    
    @Override
    public List<T> findAll() {
        return repository.findByDeleted(false);
    }

    @Override
    public T findById(ID id) {
        Optional<T> entity = repository.findByIdAndDeleted(id, false);
        if (!entity.isPresent()) {
            throw new EntityIsNotPresent();
        }
        return entity.get();
    }

    @Override
    public T update(ID id, T entity) {
        findById(id);
        setId(entity, id);
        return create(entity);
    }

    @Override
    public void delete(ID id) {
        T entity = findById(id);
        entity.delete();
        create(entity);
    }

    @Override
    public void switchActivation(ID id) {
        T entity = findById(id);
        entity.switchActivation();
        create(entity);

    }

    private void setId(T entity, ID id) {
        Field idField = ReflectionUtils.findField(BaseEntity.class, "id");
        ReflectionUtils.makeAccessible(idField);
        ReflectionUtils.setField(idField, entity, id);

    }

    protected abstract BaseRepository<T, ID> getRepository();

}
