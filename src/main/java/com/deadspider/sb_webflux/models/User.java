package com.deadspider.sb_webflux.models;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;
    
    private String password;


    public static User fromDTO(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
        /*return User.builder()
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .username(dto.getUsername())
            .password(dto.getPassword())
            .email(dto.getEmail())
        .build();*/
    }

}
