package com.t3h.group3_petshop.service.impl;


import com.t3h.group3_petshop.entity.ProductEntity;
import com.t3h.group3_petshop.entity.RoleEntity;
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
    public void saveUser(UserEntity user) {
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
    public BaseResponse<?> deleteUser(Long userId) {
        // Log thông báo bắt đầu xóa sản phẩm
        logger.info("Start delete product with id {}", userId);

        // Tạo một đối tượng BaseResponse để lưu kết quả
        BaseResponse<String> baseResponse = new BaseResponse<>();

        // Tìm sản phẩm trong cơ sở dữ liệu theo ID
        Optional<UserEntity> optionalUser= userRepository.findById(userId);

        // Kiểm tra xem sản phẩm có tồn tại không
        if (optionalUser.isEmpty()) {
            // Nếu không tồn tại, trả về mã trạng thái NOT_FOUND và thông báo lỗi
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage(" user not found");
            return baseResponse;
        }

        // Lấy sản phẩm từ Optional
        UserEntity user = optionalUser.get();

        // Đặt trạng thái deleted của sản phẩm thành true
        user.setDeleted(true);

        // Lưu lại sản phẩm đã xóa vào cơ sở dữ liệu
        userRepository.save(user);

        // Thiết lập kết quả thành công và trả về thông báo xóa thành công
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage("User deleted successfully");
        return baseResponse;
    }
}
