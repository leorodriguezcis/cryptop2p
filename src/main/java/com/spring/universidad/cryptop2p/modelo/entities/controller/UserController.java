package com.spring.universidad.cryptop2p.modelo.entities.controller;

import com.spring.universidad.cryptop2p.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.universidad.cryptop2p.modelo.entities.dto.UserRegisterDto;
import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping(value="/api/auth/register")
    public ResponseEntity<String> userRegister(@Valid @RequestBody UserRegisterDto user){
        System.out.println(user.getName());
        userService.registerUser(user);
        return ResponseEntity.ok(user.getName());
    }

}
