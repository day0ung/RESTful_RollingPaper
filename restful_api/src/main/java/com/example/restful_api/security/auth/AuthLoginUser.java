package com.example.restful_api.security.auth;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class AuthLoginUser extends User {

    String email;
    String password;
    public AuthLoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
    }
}
