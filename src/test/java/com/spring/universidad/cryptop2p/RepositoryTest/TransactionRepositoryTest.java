package com.spring.universidad.cryptop2p.RepositoryTest;
import com.spring.universidad.cryptop2p.data.DatosDummy;
import com.spring.universidad.cryptop2p.model.entities.Transaction;
import com.spring.universidad.cryptop2p.model.entities.User;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import com.spring.universidad.cryptop2p.model.enums.TransactionState;
import com.spring.universidad.cryptop2p.model.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    void setup() {
        User mockUser = new User("name",  "lastname",  "email@jotmail.com",  "address",  "123456789Leo.-",  "cvasdasdasdasdu",  "walasdasdasdasdlet");
        Transaction trans3 = DatosDummy.getTransaction3();
        trans3.setIsActive(TransactionState.FINISHED);
        trans3.setUser(mockUser);
        trans3.setTransactionDate(LocalDateTime.of(2023,6,13,12,12,12));
        Transaction trans2 = DatosDummy.getTransaction2();
        trans2.setIsActive(TransactionState.CANCELLED);
        Transaction trans1 = DatosDummy.getTransaction1();
        trans1.setIsActive(TransactionState.NEW);
        transactionRepository.save(DatosDummy.getTransaction1());
        transactionRepository.save(DatosDummy.getTransaction2());
        transactionRepository.save(DatosDummy.getTransaction3());
    }
    
    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
    }

    @Test
    @DisplayName("find transaction with crypto type")
    void findTransactionByCryptoType() {
        Iterable<Transaction> expected = transactionRepository.transactionByCryptoName(CryptoEnum.ETHUSDT);
        assertThat(((List<Transaction>)expected).size() == 1).isSameAs(true);
        assertThat(((List<Transaction>)expected).get(0).getCrypto().getName() == CryptoEnum.ETHUSDT).isSameAs(true);
    }

    @Test
    @DisplayName("get all active transactions")
    void transactionsActive() {
        Iterable<Transaction> expected = transactionRepository.transactionsActive();
        assertThat(((List<Transaction>)expected).size() == 1).isSameAs(true);
    }


    @Test
    @DisplayName("get transactions of an user in a range of date")
    void testFindOrdersByUserId() {
        LocalDateTime start = LocalDateTime.of(2022,6,10,12,12,12);
        LocalDateTime end = LocalDateTime.of(2023,6,15,12,12,12);
        Iterable<Transaction> expected = transactionRepository.searchByRangeActivity(start,end,3);
        assertThat(((List<Transaction>)expected).size() == 1).isSameAs(true);
    }
}
