package com.example.restful_api.service;

import com.example.restful_api.api.dto.user.UserPostRequest;
import com.example.restful_api.api.dto.user.UserPutRequest;
import com.example.restful_api.api.dto.user.UserResponse;
import com.example.restful_api.domain.user.Provider;
import com.example.restful_api.domain.user.Role;
import com.example.restful_api.domain.user.User;
import com.example.restful_api.domain.user.UserRepository;
import com.example.restful_api.exception.ResourceNotFoundException;
import com.example.restful_api.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signUp(UserPostRequest request){
        userRepository.findByEmail(request.getEmail()).ifPresent( user -> {
            throw new DataIntegrityViolationException(String.format("Email Already exists,  {'%s'}", user.getEmail()));
        });

        String encodePassword = passwordEncoder.encode(request.getPassword());

        User user = userRepository.save(request.toEntity(encodePassword, Role.USER, Provider.LOCAL));

        return new UserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(CustomUserPrincipal customUserPrincipal, UserPutRequest request) {
        String email = customUserPrincipal.getUser().getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(User.class.getSimpleName(), "email", email)
        );
        user.updateName(request.getName());

        return new UserResponse(user);
    }

    @Transactional
    public UserResponse deleteUser(CustomUserPrincipal customUserPrincipal) {
        String email = customUserPrincipal.getUser().getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(User.class.getSimpleName(), "email", email)
        );

        userRepository.deleteById(user.getId());

        return new UserResponse(user);
    }

    public UserResponse getUser(CustomUserPrincipal customUserPrincipal) {
        String email = customUserPrincipal.getUser().getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(User.class.getSimpleName(), "email", email)
        );

        return null;
    }
}
