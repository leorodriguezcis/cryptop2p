package com.spring.universidad.cryptop2p.model.controller;

import com.spring.universidad.cryptop2p.model.dto.UserLoginDTO;
import com.spring.universidad.cryptop2p.services.implementation.UserService;
import com.spring.universidad.cryptop2p.model.dto.UserRegisterDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import io.swagger.annotations.Api;
import java.util.Map;

@RestController
@Api(tags = "User")
public class UserController {
    @Autowired
    UserService service;

    @ApiOperation(value = "register user")
    @PostMapping(value="/register")
    public ResponseEntity<String> userRegister(@Valid @RequestBody UserRegisterDTO user){
        service.registerUser(user);
        return ResponseEntity.ok(user.getName());
    }

    @ApiOperation(value = "login user")
    @PostMapping(value="/login")
    public ResponseEntity<Map<String, Object>> userLogin(@Valid @RequestBody UserLoginDTO user){
        Map<String, Object> message = service.logIn(user);
        if(message.get("SUCCESS").equals(Boolean.FALSE))
            return ResponseEntity.badRequest().body(message);
        return ResponseEntity.ok(message);
    }

}
