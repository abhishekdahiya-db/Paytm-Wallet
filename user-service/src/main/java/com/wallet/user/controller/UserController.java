package com.wallet.user.controller;

import com.wallet.user.DTO.UserDTO;
import com.wallet.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<Long> registerNewUser(@RequestBody @Valid UserDTO userRequest){
        Long userId = userService.registerUser(userRequest);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }
}
