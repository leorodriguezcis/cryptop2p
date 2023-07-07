package com.spring.universidad.cryptop2p.services;

import com.spring.universidad.cryptop2p.data.DatosDummy;
import com.spring.universidad.cryptop2p.model.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.model.entities.Transaction;
import com.spring.universidad.cryptop2p.model.entities.User;
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
class TransactionServiceTest {
    private TransactionDTO trans1;
    private TransactionDTO trans2;
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
        trans2 = new TransactionDTO(TransactionType.BUY, CryptoEnum.ETHUSDT,1d,879000d,"chaco lopez",new BigDecimal(11831) );
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
        User user1 = userRepository.findAll().iterator().next();
        transactionService.addTransaction(trans1, user1.getId());
        Iterable<Transaction> res = transactionRepository.findAll();
        ArrayList<Transaction> resList = (ArrayList<Transaction>)res;
        assertThat(resList).hasSize(1);
    }

    @Test
    @DisplayName("buy an intention active")
    void buyAnIntentionTest() {
        User user1 = userRepository.findAll().iterator().next();
        User user2 = ((ArrayList<User>)userRepository.findAll()).get(1);
        transactionService.addTransaction(trans1, user1.getId());
        Transaction resList = ((ArrayList<Transaction>)transactionRepository.findAll()).get(0);
        transactionService.sellOrBuyAnIntention(user2.getId(), resList.getId(), TransactionType.BUY,user2.getEmail() );
        Optional<Transaction> res = transactionRepository.findById(resList.getId());
        Transaction resTransaction = res.get();
        assertThat(resTransaction.getIsActive()).isEqualTo(TransactionState.ON_PROCESS);
    }

    @Test
    @DisplayName("sell an intention active")
    void sellAnIntentionTest() {
        User user1 = userRepository.findAll().iterator().next();
        User user2 = ((ArrayList<User>)userRepository.findAll()).get(1);
        transactionService.addTransaction(trans2, user1.getId());
        Transaction resList = ((ArrayList<Transaction>)transactionRepository.findAll()).get(0);
        transactionService.sellOrBuyAnIntention(user2.getId(), resList.getId(), TransactionType.SELL, user2.getEmail() );
        Optional<Transaction> res = transactionRepository.findById(resList.getId());
        Transaction resTransaction = res.get();
        assertThat(resTransaction.getIsActive()).isEqualTo(TransactionState.ON_PROCESS);
    }
    @Test
    @DisplayName("confirm send")
    void confirmSendTest() {
        User user1 = userRepository.findAll().iterator().next();
        User user2 = ((ArrayList<User>)userRepository.findAll()).get(1);
        transactionService.addTransaction(trans2, user1.getId());
        Transaction resList = ((ArrayList<Transaction>)transactionRepository.findAll()).get(0);
        transactionService.sellOrBuyAnIntention(user2.getId(), resList.getId(), TransactionType.SELL, user2.getEmail() );
        transactionService.confirmTransference(user2.getId(), resList.getId(), "send", user2.getEmail());
        Optional<Transaction> res = transactionRepository.findById(resList.getId());
        Transaction resTransaction = res.get();
        assertThat(resTransaction.getIsActive()).isEqualTo(TransactionState.CONFIRMED);
    }
    @Test
    @DisplayName("confirm receive")
    void confirmReceiveTest() {
        User user1 = userRepository.findAll().iterator().next();
        User user2 = ((ArrayList<User>)userRepository.findAll()).get(1);
        transactionService.addTransaction(trans2, user1.getId());
        Transaction resList = ((ArrayList<Transaction>)transactionRepository.findAll()).get(0);
        transactionService.sellOrBuyAnIntention(user2.getId(), resList.getId(), TransactionType.SELL, user2.getEmail() );
        transactionService.confirmTransference(user2.getId(), resList.getId(), "send", user2.getEmail());
        transactionService.confirmTransference(user1.getId(), resList.getId(), "receive", user1.getEmail());
        Optional<Transaction> res = transactionRepository.findById(resList.getId());
        Transaction resTransaction = res.get();
        assertThat(resTransaction.getIsActive()).isEqualTo(TransactionState.FINISHED);
    }
    @Test
    @DisplayName("cancel transaction")
    void cancelTransactionTest() {
        User user1 = userRepository.findAll().iterator().next();
        User user2 = ((ArrayList<User>)userRepository.findAll()).get(1);
        transactionService.addTransaction(trans2, user1.getId());
        Transaction resList = ((ArrayList<Transaction>)transactionRepository.findAll()).get(0);
        transactionService.sellOrBuyAnIntention(user2.getId(), resList.getId(), TransactionType.SELL, user2.getEmail() );
        transactionService.cancelTransaction(user2.getId(), resList.getId(),user1.getEmail());
        Optional<Transaction> res = transactionRepository.findById(resList.getId());
        Transaction resTransaction = res.get();
        assertThat(resTransaction.getIsActive()).isEqualTo(TransactionState.CANCELLED);
    }
    
}
     
