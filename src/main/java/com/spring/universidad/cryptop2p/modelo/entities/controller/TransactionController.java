package com.spring.universidad.cryptop2p.modelo.entities.controller;

import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.services.interfaces.CryptoDAO;
import com.spring.universidad.cryptop2p.services.interfaces.TransactionDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Api(tags = "Transaction")
public class TransactionController extends GenericController<Transaction, TransactionDAO>{
    private final CryptoDAO cryptoDAO;
    @Autowired
    public TransactionController(TransactionDAO service,CryptoDAO cryptoDAO ) {
        super(service);
        this.cryptoDAO = cryptoDAO;
    }

    @ApiOperation(value = "Transactions")
    @PostMapping(value="/transaction/new")
    public ResponseEntity<String> createTransaction(@Valid @RequestBody TransactionDTO transaction){
        Optional<Crypto> crypto = cryptoDAO.findCryptoByName(transaction.getCryptoType());
        Transaction transactionModel = service.addTransaction(transaction);
        transactionModel.setCrypto(crypto.get());
        service.save(transactionModel);
        return ResponseEntity.ok(transaction.getUser());
    }
}
