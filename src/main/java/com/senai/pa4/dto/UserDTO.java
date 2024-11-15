package com.senai.pa4.dto;

import com.senai.pa4.entities.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;

//    @NotBlank(message = "Campo obrigatório")
    private String username;

//    @NotBlank(message = "Campo obrigatório")
    private String position;

//    @NotBlank(message = "Campo obrigatório")
    private String roleType;

//    @NotBlank(message = "Campo obrigatório")
    private String password;

    public UserDTO(User entity) {
        id = entity.getId();
        username = entity.getUsername();
        position = entity.getPosition();
        roleType = entity.getRoleType();
        password = entity.getPassword(); // INSERIDO NO PA4
    }
}
