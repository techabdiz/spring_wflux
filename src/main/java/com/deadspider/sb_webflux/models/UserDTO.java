package com.deadspider.sb_webflux.models;

import org.springframework.beans.BeanUtils;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;


    @NotNull
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    @Email(message = "invalid email")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 200, message = "username should be atleast 4 and not longer than 200 characters")
    
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 200, message = "Password should be atleast 8 characters long")
    private String password;

    public static UserDTO fromEntity(User entity) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setId(entity.getId().toString());
        return dto;
    }
}
