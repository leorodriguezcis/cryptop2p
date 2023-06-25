package com.spring.universidad.cryptop2p.RepositoryTest;

import com.spring.universidad.cryptop2p.data.DatosDummy;
import com.spring.universidad.cryptop2p.model.entities.Crypto;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import com.spring.universidad.cryptop2p.model.repository.CryptoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CryptoRepositoryTest {
    @Autowired
    CryptoRepository cryptoRepository;

    @BeforeEach
    void setup() {
        cryptoRepository.save(DatosDummy.getATOMUSDT());
    }
    @AfterEach
    void tearDown() {
        cryptoRepository.deleteAll();
    }
    @Test
    @DisplayName("get crypto for name")
    void transactionsActive() {
        Crypto expected = cryptoRepository.findCryptosByName(CryptoEnum.ATOMUSDT).get();
        assertThat(expected.getId()).isEqualTo(1);
    }
}
