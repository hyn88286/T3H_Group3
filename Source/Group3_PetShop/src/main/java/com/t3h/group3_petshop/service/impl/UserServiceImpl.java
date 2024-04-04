package com.t3h.group3_petshop.service.impl;


import com.t3h.group3_petshop.entity.RoleEntity;
import com.t3h.group3_petshop.entity.SizeEntity;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.RoleDTO;
import com.t3h.group3_petshop.model.dto.UserDTO;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.RoleRepository;
import com.t3h.group3_petshop.repository.UserRepository;
import com.t3h.group3_petshop.service.IUserService;
import com.t3h.group3_petshop.utils.Constant;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
        logger.info("Tạo ra bean: {}", UserServiceImpl.class);
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        UserDTO userDto = modelMapper.map(userEntity, UserDTO.class);
        List<RoleDTO> roleDtos = roleRepository.getRoleByUsername(username).stream().map(role -> modelMapper.map(role, RoleDTO.class)).toList();
        userDto.setRoleDTOS(roleDtos);
        return userDto;
    }
    @Override
    public void addUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Gán mặc định vai trò cho người dùng (ví dụ: ROLE_USER)
        // Bạn có thể tùy chỉnh logic gán vai trò ở đây
        RoleEntity userRole = new RoleEntity();
        userRole.setName("ROLE_USER");
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);
    }

    @Override
    public BaseResponse<?> getAllUsers() {
        BaseResponse<List<UserEntity>> baseResponse = new BaseResponse<>();
        baseResponse.setData(userRepository.userId());
        return baseResponse;
    }


    @Override
    public void update(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public BaseResponse<?> deleteUser(Long userId) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if (optionalUserEntity.isEmpty()) {
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

    @Override
    public UserDTO getCurrentUser(Boolean showId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                // Lấy thông tin người dùng từ Principal
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                // Lấy giá trị username đăng nhập
                String username = userDetails.getUsername();
                // Lấy dữ liệu bản ghi user đang đăng nhập
                userEntity = userRepository.findByUsername(username);
                // Set giá trị userId cho filter
            }
        }
        UserDTO userDTO = new UserDTO();
        if (userEntity != null) {
            userDTO.setUsername(userEntity.getUsername());
            if(showId){
                userDTO.setId(userEntity.getId());
            }
        }

        return userDTO;
    }




}
