package com.example.restful_api.service;

import com.example.restful_api.domain.user.User;
import com.example.restful_api.domain.user.UserRepository;
import com.example.restful_api.exception.ResourceNotFoundException;
import com.example.restful_api.security.CustomUserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BaseService {

    protected <T> T verifyId(Long entityId, JpaRepository<T, Long> repository, Class<T> entityClass) {
        T entity = repository.findById(entityId).orElseThrow(
                () -> new ResourceNotFoundException(entityClass.getSimpleName(), "id", entityId)
        );
        return entity;
    }

    protected User verifyUser(CustomUserPrincipal customUserPrincipal, UserRepository userRepository) {
        String email = customUserPrincipal.getUser().getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(User.class.getSimpleName(), "email", email)
        );
        return user;
    }
}
