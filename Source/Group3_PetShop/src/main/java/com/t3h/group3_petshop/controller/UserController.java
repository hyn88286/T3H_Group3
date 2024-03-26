package com.t3h.group3_petshop.controller;

import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.UserDTO;
import com.t3h.group3_petshop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class UserController {

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

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user) {
        userService.updateUser(user);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }
}
