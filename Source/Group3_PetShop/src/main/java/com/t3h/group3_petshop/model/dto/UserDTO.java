package com.t3h.group3_petshop.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String email;

    List<RoleDTO> roleDTOS;
}