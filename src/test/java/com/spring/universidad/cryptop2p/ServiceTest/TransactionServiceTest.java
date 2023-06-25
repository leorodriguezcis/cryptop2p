package com.spring.universidad.cryptop2p.ServiceTest;

import com.spring.universidad.cryptop2p.data.DatosDummy;
import com.spring.universidad.cryptop2p.model.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.model.entities.Transaction;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import com.spring.universidad.cryptop2p.model.enums.TransactionState;
import com.spring.universidad.cryptop2p.model.enums.TransactionType;
import com.spring.universidad.cryptop2p.model.repository.CryptoRepository;
import com.spring.universidad.cryptop2p.model.repository.TransactionRepository;
import com.spring.universidad.cryptop2p.model.repository.UserRepository;
import com.spring.universidad.cryptop2p.services.implementation.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;



@DataJpaTest
public class TransactionServiceTest {
    private TransactionDTO trans1;
    TransactionService transactionService;
    
    @Autowired
    UserRepository userRepository;
    @Autowired
    CryptoRepository cryptoRepo;
    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp(){
        trans1 = new TransactionDTO(TransactionType.SELL, CryptoEnum.ETHUSDT,1d,879000d,"chaco lopez",new BigDecimal(11831) );
        transactionService = new TransactionService(transactionRepository, userRepository, cryptoRepo);
        userRepository.save(DatosDummy.getUser1());
        userRepository.save(DatosDummy.getUser2());
        cryptoRepo.save(DatosDummy.getETHUSDT());
        cryptoRepo.save(DatosDummy.getAXSUSDT());
        }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
        cryptoRepo.deleteAll();
        transactionRepository.deleteAll();
    }

    @Test
    @DisplayName("add transaction")
    void addTransaction() {
        transactionService.addTransaction(trans1, 6);
        Iterable<Transaction> res = transactionRepository.findAll();
        ArrayList<Transaction> resList = (ArrayList<Transaction>)res;
        assertThat(resList).hasSize(1);
    }

    @Test
    @DisplayName("buy an intention active")
    void buyAnIntentionTest() {
        transactionService.addTransaction(trans1, 7);
        transactionService.sellOrBuyAnIntention(8, 11, null );
        Optional<Transaction> res = transactionRepository.findById(11);
        Transaction resTransaction = res.get();
        assertThat(resTransaction.getIsActive()).isEqualTo(TransactionState.ON_PROCESS);
    }
    //TRANSACCION 11 USER 7 y 8

}
     