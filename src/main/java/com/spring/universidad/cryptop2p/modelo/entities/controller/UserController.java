package com.spring.universidad.cryptop2p.modelo.entities.controller;

import com.spring.universidad.cryptop2p.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.universidad.cryptop2p.modelo.entities.dto.UserRegisterDto;
import javax.validation.Valid;
import io.swagger.annotations.Api;
@RestController
@Api(tags = "Register")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "register user")
    @PostMapping(value="/api/auth/register")
    public ResponseEntity<String> userRegister(@Valid @RequestBody UserRegisterDto user){
        System.out.println(user.getName());
        userService.registerUser(user);
        return ResponseEntity.ok(user.getName());
    }

}
