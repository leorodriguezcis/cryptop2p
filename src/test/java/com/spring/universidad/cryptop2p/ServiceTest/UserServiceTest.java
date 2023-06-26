package com.spring.universidad.cryptop2p.ServiceTest;

import com.spring.universidad.cryptop2p.model.dto.UserLoginDTO;
import com.spring.universidad.cryptop2p.model.dto.UserRegisterDTO;
import com.spring.universidad.cryptop2p.model.entities.User;
import com.spring.universidad.cryptop2p.model.repository.UserRepository;
import com.spring.universidad.cryptop2p.services.implementation.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@DataJpaTest
class UserServiceTest {
    private UserLoginDTO userLogin;
    private UserRegisterDTO userTest;
    private UserService userService ;
    
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        userLogin = new UserLoginDTO("chaco","1234Asd");
        userTest = new UserRegisterDTO("chaco","lopez","chaaaco@gmail.com","123","1234Asd","1234567891234567891234","asdf");
        userService = new UserService(userRepository);
    }
    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("register user")
    void registerTest() {
        userService.registerUser(userTest);
        assertThat(((ArrayList<User>)userRepository.findAll()).get(0).getName()).isEqualTo("chaco");
    }
    @Test
    @DisplayName("login user")
    void loginTest() {
        userService.registerUser(userTest);
        System.out.println("ASD"+userRepository.findByName("chaco"));
        Map<String, Object> message = userService.logIn(userLogin);
        Map<String, Object> messageRes = new HashMap<>(); 
        messageRes.put("SUCCESS", Boolean.TRUE);
        Object success = message.get("SUCCESS");
        assertThat(success).isEqualTo(messageRes.get("SUCCESS"));
    }
}
     
