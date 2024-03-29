package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.UserDTO;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserResource {
    @Autowired
    private IUserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        UserDTO user = userService.findUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserEntity user) {
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK.value()).body("User created successfully");
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<BaseResponse<?>> deleteUser(@PathVariable("userId") Long userId) {
        BaseResponse<?> response = userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
