package com.spring.universidad.cryptop2p.modelo.entities.controller;

import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.services.interfaces.CryptoDAO;
import com.spring.universidad.cryptop2p.services.interfaces.TransactionDAO;
import com.spring.universidad.cryptop2p.services.interfaces.UserDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Api(tags = "Transaction")
public class TransactionController extends GenericController<Transaction, TransactionDAO>{
    private final CryptoDAO cryptoDAO;
    private final UserDAO userDAO;
    @Autowired
    public TransactionController(TransactionDAO service, CryptoDAO cryptoDAO, UserDAO userDAO ) {
        super(service);
        this.cryptoDAO = cryptoDAO;
        this.userDAO = userDAO;
    }

    @ApiOperation(value = "create")
    @PostMapping(value="/transaction/new")
    public ResponseEntity<String> createTransaction(@Valid @RequestBody TransactionDTO transaction){
        Optional<Crypto> crypto = cryptoDAO.findCryptosByName(transaction.getCryptoType());
        Optional<User> user = userDAO.findUsersByName(transaction.getUser());
        Transaction transactionModel = service.addTransaction(transaction);
        transactionModel.setCrypto(crypto.get());
        transactionModel.setUser(user.get());
        service.save(transactionModel);
        return ResponseEntity.ok(transaction.getUser());
    }
    @ApiOperation(value = "get by cryptoName")
    @GetMapping(value="/transaction/{crypto}")
    public ResponseEntity<Iterable<Transaction>> searchTransactionByCrypto( @PathVariable CryptoEnum crypto){
        Iterable<Transaction> transactions = service.transactionByCryptoName(crypto);
        return ResponseEntity.ok(transactions);
    }

    @ApiOperation(value = "get by cryptoName")
    @GetMapping(value="/transaction/getActives")
    public ResponseEntity<Iterable<Transaction>> searchTransactionsActive(){
        Iterable<Transaction> transactions = service.transactionsActive();
        return ResponseEntity.ok(transactions);
    }
}
