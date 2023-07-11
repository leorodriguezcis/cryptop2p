package com.spring.universidad.cryptop2p.model.entities;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
@SpringBootTest
@AutoConfigureTestDatabase
class TransactionTest {
    @Test
    void transactionValidateTestIfAmountIsBetween(){
        Crypto cryptoMock = mock(Crypto.class);
        when(cryptoMock.getValue()).thenReturn(100d);
        Transaction trans = new Transaction();
        Assert.assertEquals(BigDecimal.valueOf(105d),trans.validateCotization(BigDecimal.valueOf(105), cryptoMock));
        Assert.assertEquals(BigDecimal.valueOf(96d),trans.validateCotization(BigDecimal.valueOf(96), cryptoMock));
        Assert.assertEquals(BigDecimal.valueOf(95d),trans.validateCotization(BigDecimal.valueOf(95), cryptoMock));
    }

    @Test
    void transactionValidateTestAmountgreaterthan(){
        Crypto cryptoMock = mock(Crypto.class);
        when(cryptoMock.getValue()).thenReturn(100d);
        Transaction trans = new Transaction();
        Assert.assertEquals(BigDecimal.valueOf(105d),trans.validateCotization(BigDecimal.valueOf(1010), cryptoMock));
    }

    @Test
    void transactionValidateTestAmountSmallerthan(){
        Crypto cryptoMock = mock(Crypto.class);
        when(cryptoMock.getValue()).thenReturn(100d);
        Transaction trans = new Transaction();
        Assert.assertEquals(BigDecimal.valueOf(95d),trans.validateCotization(BigDecimal.valueOf(90), cryptoMock));
    }

}
