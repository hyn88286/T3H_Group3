package com.the.doanthucung.service.impl;

import com.the.doanthucung.entity.UserEntity;
import com.the.doanthucung.model.dto.RoleDto;
import com.the.doanthucung.model.dto.UserDto;
import com.the.doanthucung.repository.RoleRepository;
import com.the.doanthucung.repository.UserEntityRepository;
import com.the.doanthucung.service.IuserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements IuserService {
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public UserDto findUserByUsername(String username) {
        UserEntity userEntity = userEntityRepository.findByUsername(username);
        UserDto userDto = modelMapper.map(userEntity,UserDto.class);
        List<RoleDto> roleDtos = roleRepository.getRoleByUsername(username).stream().map(role -> modelMapper.map(role,RoleDto.class)).toList();
//        List<RoleDto> roleDtos = new ArrayList<>();
//        userEntity.getRoleEntities().forEach(role -> {
//            RoleDto roleDto = modelMapper.map(role,RoleDto.class);
//            roleDtos.add(roleDto);
//        });
        userDto.setRoleDtos(roleDtos);
        return userDto;
    }
}
