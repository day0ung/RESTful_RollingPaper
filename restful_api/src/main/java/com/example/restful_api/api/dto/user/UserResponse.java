package com.example.restful_api.api.dto.user;


import com.example.restful_api.domain.user.Role;
import com.example.restful_api.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    Long id;

    String name;

    Role role;

    public UserResponse(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.role = user.getRole();
    }
}
