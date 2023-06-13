package com.spring.universidad.cryptop2p.RepositoryTest;
import com.spring.universidad.cryptop2p.data.DatosDummy;
import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.modelo.entities.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    void setup() {
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
        System.out.println(expected+"holala");
        assertThat(((List<Transaction>)expected).size() == 1).isTrue();
        assertThat(((List<Transaction>)expected).get(0).getCrypto().getName() == CryptoEnum.ETHUSDT).isTrue();
    }
}
