package com.t3h.group3_petshop.service.impl;


import com.t3h.group3_petshop.entity.OrderEntity;
import com.t3h.group3_petshop.entity.RoleEntity;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.OrderDTO;
import com.t3h.group3_petshop.model.dto.RoleDTO;
import com.t3h.group3_petshop.model.dto.UserDTO;
import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.RoleRepository;
import com.t3h.group3_petshop.repository.UserRepository;
import com.t3h.group3_petshop.service.IUserService;
import com.t3h.group3_petshop.utils.Constant;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private PasswordEncoder passwordEncoder;
    public UserServiceImpl() {
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
    public void addUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(Constant.ROLE_USER);
        userEntity.setRoles(Collections.singleton(roleEntity));
        userRepository.save(userEntity);
    }



}
