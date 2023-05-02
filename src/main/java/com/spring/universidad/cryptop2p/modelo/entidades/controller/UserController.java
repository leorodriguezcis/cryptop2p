package com.spring.universidad.cryptop2p.modelo.entidades.controller;

import com.spring.universidad.cryptop2p.modelo.entidades.User;
import com.spring.universidad.cryptop2p.services.UserService;
import com.spring.universidad.cryptop2p.services.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.universidad.cryptop2p.modelo.entidades.dto.UserRegisterDto;
import javax.validation.Valid;

@RestController
public class UserController extends GenericController<User, UserDAO>{
    @Autowired
    private UserService userService;

    
    public UserController(UserDAO service) {
        super(service);
    }

    @PostMapping(value="/api/auth/register")
    public ResponseEntity<String> userRegister(@Valid @RequestBody UserRegisterDto user){
        userService.registerUser(user);
        return ResponseEntity.ok(user.getName());
    }

}
