package com.t3h.group3_petshop.model.dto;

import com.t3h.group3_petshop.controller.resource.UserResource;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    List<RoleDTO> roleDTOS;


}