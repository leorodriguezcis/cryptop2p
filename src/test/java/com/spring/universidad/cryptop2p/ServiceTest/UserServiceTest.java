package com.spring.universidad.cryptop2p.ServiceTest;


import com.spring.universidad.cryptop2p.model.entities.User;
import com.spring.universidad.cryptop2p.model.dto.UserRegisterDTO;
import com.spring.universidad.cryptop2p.services.implementation.UserService;
import org.junit.After;
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
    private User user;
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception{
         userTest = new UserRegisterDTO("chaco","lopez","chaaaco@gmail.com","123","1234","1234567891234567891234","asdf");
    }
    @After
    public void tearDown(){
        userService.deleteById(user.getId());
    }

    @Test
    public void checkRegisterUser() throws Exception {
        this.user = userService.registerUser(userTest);
        Assert.assertEquals("chaco",userService.findById(user.getId()).get().getName());
    }

}
