package com.spring.universidad.cryptop2p.model.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.spring.universidad.cryptop2p.data.DatosDummy;
import com.spring.universidad.cryptop2p.model.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @BeforeEach
    void setup(){
        userRepository.save(DatosDummy.getUser1());
    }

    @Test
    void testCreateUser() throws Exception {
        String jsonBody = "{\r\n" + //
                "    \"name\": \"leo3\",\r\n" + //
                "    \"lastName\": \"leo1\",\r\n" + //
                "    \"email\": \"leo1@leo.com\",\r\n" + //
                "    \"address\": \"leo 1772\",\r\n" + //
                "    \"password\": \"randomPassword\",\r\n" + //
                "    \"cvu\": \"123123123123\",\r\n" + //
                "    \"addrWallet\":\"12312312312\"\r\n" + //
                "}";
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginUser() throws Exception {
        
        String jsonBody = "{\"email\":\"elpepe@gmail.com\",\"password\":\"1234\"}";
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());
    }

}