package com.t3h.group3_petshop.controller.resource;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.repository.UserRepository;
import com.t3h.group3_petshop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class RegisterResource {
    @Autowired
     private IUserService iUserService;

   @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserEntity userEntity){
       iUserService.saveUser(userEntity);
       return ResponseEntity.status(HttpStatus.CREATED).body("account");

   }

}
