package com.example.restful_api.security;

import com.example.restful_api.domain.user.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomUserPrincipal implements OAuth2User, UserDetails {

    private Users users;
    private Map<String, Object> attributes;


    //일반 로그인
    public CustomUserPrincipal(Users users){
        this.users = users;
    }

    //Oauth 로그인
    public CustomUserPrincipal(Users users, Map<String,Object> attributes){
        this.users = users;
        this.attributes = attributes;
    }

    public Users getUser() {
        return users;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> users.getRole().getKey());
        return collection;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
