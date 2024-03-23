package com.t3h.group3_petshop.service.impl;


import com.t3h.group3_petshop.entity.RoleEntity;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.RoleDTO;
import com.t3h.group3_petshop.model.dto.UserDTO;
import com.t3h.group3_petshop.repository.RoleRepository;
import com.t3h.group3_petshop.repository.UserRepository;
import com.t3h.group3_petshop.service.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public void saveUser(UserEntity user) {
        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Gán mặc định vai trò cho người dùng (ví dụ: ROLE_USER)
        // Bạn có thể tùy chỉnh logic gán vai trò ở đây
        RoleEntity userRole = new RoleEntity();
        userRole.setName("ROLE_USER");
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);
    }

    @Override
    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
        }
    }
}
