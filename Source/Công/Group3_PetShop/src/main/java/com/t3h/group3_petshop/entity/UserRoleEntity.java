package com.t3h.group3_petshop.entity;

import jakarta.persistence.Column;

public class UserRoleEntity {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;
}
