package com.t3h.group3_petshop.service;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.UserDTO;
import com.t3h.group3_petshop.model.request.UserRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.data.domain.Page;

public interface IUserService {
    UserDTO findUserByUsername(String username);
    BaseResponse<?> addUser(UserEntity user);
    BaseResponse<Page<UserDTO>>getAllUsers(UserRequest filterRequest, int page, int size);
    BaseResponse update(Long id ,UserEntity user);
    BaseResponse<?> deleteUser(Long id);
    UserDTO getCurrentUser(Boolean showId);



}
