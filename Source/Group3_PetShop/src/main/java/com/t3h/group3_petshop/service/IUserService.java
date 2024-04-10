package com.t3h.group3_petshop.service;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.UserDTO;
import com.t3h.group3_petshop.model.response.BaseResponse;

import java.util.List;

public interface IUserService {
    UserDTO findUserByUsername(String username);
    BaseResponse<?> addUser(UserEntity user);
    BaseResponse<?>getAllUsers();
    BaseResponse update(Long id ,UserEntity user);
    BaseResponse<?> deleteUser(Long userId);
    UserDTO getCurrentUser(Boolean showId);

}
