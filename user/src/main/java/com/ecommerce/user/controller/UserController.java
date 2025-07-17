package com.ecommerce.user.controller;


import com.ecommerce.user.dto.UserReqDtos;
import com.ecommerce.user.dto.UserResDto;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResDto>> getAllUsers(){
        List<UserResDto> users = userService.getAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok().body(users);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserReqDtos user){
        userService.createUser(user);
        return ResponseEntity.ok().body("User created successfully");
    }

    @GetMapping("/{id}")
    public UserResDto getUser(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserReqDtos user){
        boolean isUpdated = userService.updateUserById(id,user);
        if(isUpdated){
            return ResponseEntity.ok().body("User updated successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
