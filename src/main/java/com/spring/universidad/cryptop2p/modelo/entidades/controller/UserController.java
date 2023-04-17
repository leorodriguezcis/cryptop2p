package com.spring.universidad.cryptop2p.modelo.entidades.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.UserService;
import com.spring.universidad.cryptop2p.modelo.entidades.User;
import com.spring.universidad.cryptop2p.modelo.entidades.dto.UserRegisterDto;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Autowired(required=false)
    private UserService userService;

    @PostMapping(value="/api/auth/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDto user) {
           User userDetail = userService.registerUser(user);
           String response= userDetail.getName();
           return ResponseEntity.ok().body(response);

    }
    @GetMapping(value="/api")
    public ResponseEntity<String> getUsers() {

        return ResponseEntity.ok().body("holaMundoLawea");
    }
    @GetMapping("/hola")
    public String holaMundo() {
        return "Hola Mundo";
    }

}
