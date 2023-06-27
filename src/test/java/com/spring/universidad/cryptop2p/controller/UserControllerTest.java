package com.spring.universidad.cryptop2p.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateUser() throws Exception {
        String jsonBody = "{\r\n" + //
                "    \"name\": \"leo3\",\r\n" + //
                "    \"lastName\": \"leo1\",\r\n" + //
                "    \"email\": \"leo@leo.com.leo\",\r\n" + //
                "    \"address\": \"leo 1772\",\r\n" + //
                "    \"password\": \"1234leo\",\r\n" + //
                "    \"cvu\": \"123123123123\",\r\n" + //
                "    \"addrWallet\":\"12312312312\"\r\n" + //
                "}";
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    void testLoginUser() throws Exception {
        String jsonBody = "{\r\n" + //
                "    \"password\": \"randomPassword\",\r\n" + //
                "    \"username\": \"RandomUser\",\r\n" + //
                "}";
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}