package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.UserDTO;
public interface IUserService {
    UserDTO findUserByUsername(String username);
     void addUser(UserEntity userEntity);
}
