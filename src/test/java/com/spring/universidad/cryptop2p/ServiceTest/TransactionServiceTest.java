package com.spring.universidad.cryptop2p.ServiceTest;

import com.spring.universidad.cryptop2p.model.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import com.spring.universidad.cryptop2p.services.implementation.TransactionService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class TransactionServiceTest {
    TransactionDTO trans1;
    @Autowired
    private TransactionService transactionService;
    @Before
    public void setUp() throws Exception{
        trans1 = new TransactionDTO("sell", CryptoEnum.ETHUSDT,1d,879000d,"chaco lopez",new BigDecimal(11831) );
        }

}
