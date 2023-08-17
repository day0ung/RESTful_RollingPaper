package com.example.restful_api.service;

import com.example.restful_api.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseService {

    @Transactional
    protected <T> T verify(Long entityId, JpaRepository<T, Long> repository, Class<T> entityClass) {
        T entity = repository.findById(entityId).orElseThrow(
                () -> new ResourceNotFoundException(entityClass.getSimpleName(), "id", entityId)
        );
        return entity;
    }
}
