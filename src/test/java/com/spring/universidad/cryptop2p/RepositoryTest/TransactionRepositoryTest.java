package com.spring.universidad.cryptop2p.RepositoryTest;
import com.spring.universidad.cryptop2p.data.DatosDummy;
import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.TransactionState;
import com.spring.universidad.cryptop2p.modelo.entities.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
public class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
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
        assertThat(((List<Transaction>)expected).size() == 1).isTrue();
        assertThat(((List<Transaction>)expected).get(0).getCrypto().getName() == CryptoEnum.ETHUSDT).isTrue();
    }

    @Test
    @DisplayName("obtener transacciones activas")
    void transactionsActive() {
        Iterable<Transaction> expected = transactionRepository.transactionsActive();
        assertThat(((List<Transaction>)expected).size() == 1).isTrue();
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
        assertThat(((List<Transaction>)expected).size() == 1).isTrue();
    }
}
