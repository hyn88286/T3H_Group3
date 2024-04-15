package com.t3h.group3_petshop.model.request;

import lombok.Data;

@Data
public class UserRequest {
    private Long userAId;
    private String fullName;
    private String username;
    private String password;
    private String phone;
}
