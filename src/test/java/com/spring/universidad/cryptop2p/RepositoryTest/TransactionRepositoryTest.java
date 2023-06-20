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
public class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    void setup() {
        DatosDummy.getTransaction1().setIsActive(TransactionState.NEW);
        DatosDummy.getTransaction2().setIsActive(TransactionState.CANCELLED);
        DatosDummy.getTransaction3().setIsActive(TransactionState.FINISHED);
        transactionRepository.save(DatosDummy.getTransaction3());
        transactionRepository.save(DatosDummy.getTransaction2());
        transactionRepository.save(DatosDummy.getTransaction1());

    }
    @AfterEach
    void tearDown() {
        transactionRepository.deleteById(DatosDummy.getTransaction1().getId());
        transactionRepository.deleteById(DatosDummy.getTransaction2().getId());
    }

    @Test
    @DisplayName("find transaction with crypto type")
    void findTransactionByCryptoType() {
        Iterable<Transaction> expected = transactionRepository.transactionByCryptoName(CryptoEnum.ETHUSDT);
        assertThat(((List<Transaction>)expected).size() == 1).isSameAs(true);
        assertThat(((List<Transaction>)expected).get(0).getCrypto().getName() == CryptoEnum.ETHUSDT).isSameAs(true);
    }

    @Test
    @DisplayName("obtener transacciones activas")
    void transactionsActive() {
        Iterable<Transaction> expected = transactionRepository.transactionsActive();
        assertThat(((List<Transaction>)expected).size() == 1).isSameAs(true);
    }


    @Test
    public void testFindOrdersByUserId() {
        User mockUser = new User("name",  "lastname",  "email@jotmail.com",  "address",  "123456789Leo.-",  "cvasdasdasdasdu",  "walasdasdasdasdlet");
        mockUser.setId(1);
        Transaction trans = DatosDummy.getTransaction3();
        trans.setUser(mockUser);
        trans.setTransactionDate(LocalDateTime.of(2023,6,13,12,12,12));
        transactionRepository.save(trans);
        Iterable<Transaction> expected = transactionRepository.searchByRangeActivity(LocalDateTime.of(2022,6,13,12,12,12),LocalDateTime.now(),1);
        assertThat(((List<Transaction>)expected).size() == 1).isSameAs(true);
    }
}
