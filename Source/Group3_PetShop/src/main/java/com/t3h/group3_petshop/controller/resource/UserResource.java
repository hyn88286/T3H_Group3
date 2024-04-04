package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/account")
public class UserResource {
    @Autowired
    private IUserService iUserService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserEntity userEntity) {
        iUserService.addUser(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("account");
    }

    @PostMapping("/listUser")
    public ResponseEntity<?> listUser() {
        return ResponseEntity.ok(iUserService.getAllUsers());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody UserEntity userEntity) {
        try {
            userEntity.setId(id);
            iUserService.update(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<BaseResponse<?>> deleteUser(@PathVariable("userId") Long userId) {
        BaseResponse<?> response = iUserService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> currentUser() {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.getCurrentUser(false));
    }
}

