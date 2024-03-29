package com.t3h.group3_petshop.service.impl;


import com.t3h.group3_petshop.entity.RoleEntity;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.RoleDTO;
import com.t3h.group3_petshop.model.dto.UserDTO;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.RoleRepository;
import com.t3h.group3_petshop.repository.UserRepository;
import com.t3h.group3_petshop.service.IUserService;
import com.t3h.group3_petshop.utils.Constant;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        logger.info("Tạo ra bean: {}",UserServiceImpl.class);
    }
    @Override
    public UserDTO findUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        UserDTO userDto = modelMapper.map(userEntity,UserDTO.class);
        List<RoleDTO> roleDtos = roleRepository.getRoleByUsername(username).stream().map(role -> modelMapper.map(role,RoleDTO.class)).toList();
        userDto.setRoleDTOS(roleDtos);
        return userDto;
    }

    @Override
    public void addUser(UserEntity user) {
        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Gán mặc định vai trò cho người dùng (ví dụ: ROLE_USER)
        // Bạn có thể tùy chỉnh logic gán vai trò ở đây
        RoleEntity userRole = new RoleEntity();
        userRole.setName(Constant.ROLE_USER);
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public void update(UserEntity userEntity) {
     userRepository.save(userEntity);
    }

    @Override
    public BaseResponse<?> deleteUser(Long userId) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if (optionalUserEntity.isEmpty()){
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("not user");
            return baseResponse;
        }
        UserEntity user = optionalUserEntity.get();
        user.setDeleted(true);
        userRepository.save(user);

        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage("user delete succerfull");
        return baseResponse;
    }


}
