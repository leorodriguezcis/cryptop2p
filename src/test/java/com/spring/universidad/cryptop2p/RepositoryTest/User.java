package com.spring.universidad.cryptop2p.RepositoryTest;
import com.spring.universidad.cryptop2p.data.DatosDummy;
import com.spring.universidad.cryptop2p.modelo.entities.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class User {
    @Autowired
    UserRepository userRepository;
    @BeforeEach
    void setUp() {
        userRepository.save(DatosDummy.getUser1());
        userRepository.save(DatosDummy.getUser2());
        userRepository.save(DatosDummy.getUser3());
    }
    @AfterEach
    void tearDown() {
        userRepository.deleteById(DatosDummy.getUser1().getId());
        userRepository.deleteById(DatosDummy.getUser2().getId());
        userRepository.deleteById(DatosDummy.getUser3().getId());
    }
}
