package com.spring.universidad.cryptop2p.model.controller;


import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.spring.universidad.cryptop2p.model.config.JWTUtil;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class CryptoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private JWTUtil jwtUtil;

    @Test
    void deberiaCrearUsuario() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/crypto/initialize"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cryptos inicializadas"));;
    }
    @Test
    void testLoginUser() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AbGVvLmNvbSIsImlhdCI6MTY4ODY4NTc5MywiZXhwIjoxNjg5MTg1NzkzfQ.6ygznmM8kZaq8wcxftV8gO4n5ApWk0ZaW4ig8CtFqkk";
        when(jwtUtil.validateToken(token)).thenReturn(true);
        mockMvc.perform(get("/crypto/getAll")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn();
    }
    
}
