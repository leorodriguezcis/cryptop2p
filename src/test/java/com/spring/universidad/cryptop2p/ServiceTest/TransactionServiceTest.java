package com.spring.universidad.cryptop2p.ServiceTest;

import com.spring.universidad.cryptop2p.data.DatosDummy;
import com.spring.universidad.cryptop2p.model.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.model.dto.UserRegisterDTO;
import com.spring.universidad.cryptop2p.model.entities.Crypto;
import com.spring.universidad.cryptop2p.model.entities.Transaction;
import com.spring.universidad.cryptop2p.model.entities.User;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import com.spring.universidad.cryptop2p.model.enums.TransactionState;
import com.spring.universidad.cryptop2p.model.repository.CryptoRepository;
import com.spring.universidad.cryptop2p.model.repository.TransactionRepository;
import com.spring.universidad.cryptop2p.model.repository.UserRepository;
import com.spring.universidad.cryptop2p.services.implementation.TransactionService;
import com.spring.universidad.cryptop2p.services.implementation.UserService;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;


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
        trans1 = new TransactionDTO("sell", CryptoEnum.ETHUSDT,1d,879000d,"chaco lopez",new BigDecimal(11831) );
        transactionService = new TransactionService(transactionRepository, userRepository, cryptoRepo);
        userRepository.save(DatosDummy.getUser1());//ID 3
        userRepository.save(DatosDummy.getUser2());//ID 4
        cryptoRepo.save(DatosDummy.getETHUSDT());
        cryptoRepo.save(DatosDummy.getAXSUSDT());
        
    }
    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
    }
    //TRANSACCION Q SE GUARDA SE GENERA CON ID 2
    @Test
    @DisplayName("Agregar Transaccion")
    void addTransaction() {
        transactionService.addTransaction(trans1, 3);
        Iterable<Transaction> res = transactionRepository.findAll();
        ArrayList<Transaction> resList = (ArrayList<Transaction>)res;
        assertThat(resList).hasSize(1);
    }

    @Test
    @DisplayName("Comprar una transaccion activa")
    void buyAnIntentionTest() {
        transactionService.addTransaction(trans1, 3);
        System.out.println("ASD"+((ArrayList<Transaction>)transactionRepository.findAll()).get(0).getId());
        transactionService.buyAnIntention(3, 2 );
        Optional<Transaction> res = transactionRepository.findById(2);
        Transaction resTransaction = res.get();
        assertThat(resTransaction.getIsActive()).isEqualTo(TransactionState.ON_PROCESS);
    }

  
  

}
     