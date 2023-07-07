package com.spring.universidad.cryptop2p.model.entities;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
@SpringBootTest
class TransactionTest {
    @Test
    void transactionValidateTest(){
        Crypto cryptoMock = mock(Crypto.class);
        when(cryptoMock.getValue()).thenReturn(100d);
        Transaction trans = new Transaction();
        System.out.println("ASD"+trans.validateCotization(BigDecimal.valueOf(105), cryptoMock));
        Assert.assertEquals(trans.validateCotization(BigDecimal.valueOf(105), cryptoMock),BigDecimal.valueOf(105));
    }
}
