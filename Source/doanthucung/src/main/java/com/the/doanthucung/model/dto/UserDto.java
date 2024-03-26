package com.the.doanthucung.model.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String fullname;
    private String code;
    private String username;
    private String password;
    private String email;

    List<RoleDto> roleDtos;
}
