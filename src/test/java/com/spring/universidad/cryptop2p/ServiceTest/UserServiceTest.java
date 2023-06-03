package com.spring.universidad.cryptop2p.ServiceTest;


import com.spring.universidad.cryptop2p.modelo.entities.dto.UserRegisterDTO;
import com.spring.universidad.cryptop2p.services.implementation.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    private UserRegisterDTO userTest;
    private UserRegisterDTO userTest1;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception{
         userTest = new UserRegisterDTO("chaco","lopez","chaaaco@gmail.com","123","1234","1234567891234567891234","asdf");
         userTest1 = new UserRegisterDTO("leo","rodriguez","leoRodriguez@gmail.com","392","diego1234","9876543210987654321098","VACALORO");
    }

    @Test
    public void checkRegisterUser() throws Exception {

        userService.registerUser(userTest);
        Assert.assertTrue(userService.getUsers().size()>0);

    }

}
