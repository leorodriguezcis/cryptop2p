package com.spring.universidad.cryptop2p.model.entities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.universidad.cryptop2p.data.DatosDummy;

@SpringBootTest
@AutoConfigureTestDatabase
class UserTest {

    @Test
    void userCancelTransaction(){
        User user = DatosDummy.getUser2();
        user.cancelTransaction();
        Assert.assertEquals(true, 80 == user.getReputation());
    }

    @Test
    void userFinishedTransactionBefore30minutes(){
        User user = DatosDummy.getUser1();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plus(10, ChronoUnit.MINUTES);
        user.finishedTransaction(start, end);
        Assert.assertEquals(true, 110 == user.getReputation());
    }

    @Test
    void userFinishedTransactionAfter30minutes(){
        User user = DatosDummy.getUser3();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plus(35, ChronoUnit.MINUTES);
        user.finishedTransaction(start, end);
        Assert.assertEquals(true, 105 == user.getReputation());
    }
}
