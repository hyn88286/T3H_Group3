package com.t3h.group3_petshop.service;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.UserDTO;
import com.t3h.group3_petshop.model.response.BaseResponse;

import java.util.List;

public interface IUserService {
    UserDTO findUserByUsername(String username);
    void addUser(UserEntity user);

    List<UserEntity> getAllUsers();

    void update(UserEntity userEntity);

    BaseResponse<?> deleteUser(Long userId);

    UserDTO getCurrentUser();
}
