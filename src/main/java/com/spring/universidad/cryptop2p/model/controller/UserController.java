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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@Api(tags = "User")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserService service;

    @ApiOperation(value = "register user")
    @PostMapping(value="/register")
    public ResponseEntity<String> userRegister(@Valid @RequestBody UserRegisterDTO user){
        long startTime = System.nanoTime();
        service.registerUser(user);
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        logger.info("Se creo un nuevo usuario: {}. Tiempo de ejecucion: {} milisegundos", user.getName(), executionTime/1000000);
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
