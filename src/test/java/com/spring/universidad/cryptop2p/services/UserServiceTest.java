package com.spring.universidad.cryptop2p.services;

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


@DataJpaTest
class UserServiceTest {
    private UserRegisterDTO userTest;
    private UserService userService ;
    
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp(){
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
}
     