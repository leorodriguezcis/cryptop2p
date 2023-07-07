package com.spring.universidad.cryptop2p.model.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.spring.universidad.cryptop2p.model.config.JWTUtil;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
 class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private JWTUtil jwtUtil;

    @Test
    void testLoginUser() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9AbGVvLmNvbSIsImlhdCI6MTY4ODY4NTc5MywiZXhwIjoxNjg5MTg1NzkzfQ.6ygznmM8kZaq8wcxftV8gO4n5ApWk0ZaW4ig8CtFqkk";
        when(jwtUtil.validateToken(token)).thenReturn(true);
        mockMvc.perform(get("/transaction/getActives")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn();
    }
    
    
}
