package com.spring.universidad.cryptop2p.model.controller;

import com.spring.universidad.cryptop2p.model.entities.User;
import com.spring.universidad.cryptop2p.model.dto.UserRegisterDTO;
import com.spring.universidad.cryptop2p.services.interfaces.UserDAO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import io.swagger.annotations.Api;
@RestController
@Api(tags = "User")
public class UserController extends GenericController <User, UserDAO>{
    @Autowired
    public UserController(UserDAO service) {
        super(service);
    }

    @ApiOperation(value = "register user")
    @PostMapping(value="/register")
    public ResponseEntity<String> userRegister(@Valid @RequestBody UserRegisterDTO user){
        service.registerUser(user);
        return ResponseEntity.ok(user.getName());
    }

}