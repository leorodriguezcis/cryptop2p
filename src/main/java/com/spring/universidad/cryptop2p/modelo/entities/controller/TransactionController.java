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

    @ApiOperation(value = "get by cryptoName")
    @GetMapping(value="/transaction/{crypto}")
    public ResponseEntity<Iterable<Transaction>> searchTransactionByCrypto( @PathVariable CryptoEnum crypto){
        Iterable<Transaction> transactions = service.transactionByCryptoName(crypto);
        return ResponseEntity.ok(transactions);
    }

    @ApiOperation(value = "get by cryptoActive")
    @GetMapping(value="/transaction/getActives")
    public ResponseEntity<Iterable<Transaction>> searchTransactionsActive(){
        Iterable<Transaction> transactions = service.transactionsActive();
        return ResponseEntity.ok(transactions);
    }

    @ApiOperation(value = "get transaction by id")
    @GetMapping(value="/transaction/get/{transactionId}")
    public ResponseEntity<Transaction> searchTransaction(@PathVariable Integer transactionId){
        Transaction transaction = service.findById(transactionId).get();
        return ResponseEntity.ok(transaction);
    }

    @ApiOperation(value = "user sell activity ")
    @PostMapping(value="/transaction/{userId}/sell")
    public ResponseEntity<String> userSellIntention(@PathVariable Integer userId,@Valid @RequestBody TransactionDTO transaction  ){
        Optional<Crypto> crypto = cryptoDAO.findCryptosByName(transaction.getCryptoType());
        Optional<User> user = userDAO.findById(userId);
        Transaction transactionModel = service.addTransaction(transaction);
        transactionModel.setCrypto(crypto.get());
        transactionModel.setUser(user.get());
        service.save(transactionModel);
        return ResponseEntity.ok(user.get().getCvu());
    }
    @ApiOperation(value = "user buy activity ")
    @PostMapping(value="/transaction/{userId}/buy/{transactionID")
    public ResponseEntity<String> userBuyIntention(@PathVariable Integer userId,@PathVariable Integer transactionID ){
        Transaction transaction = service.findById(transactionID).get();
        transaction.setOtherUserId(userId);
        transaction.setIsActive(false);
        User user = userDAO.findById(userId).get();
        return ResponseEntity.ok(user.getWallet());
    }
    @ApiOperation(value = "user confirm transference ")
    @PostMapping(value="/transaction/{userId}/confirmTransference/{transactionID")
    public ResponseEntity<String> userConfirmTransference(@PathVariable Integer userId,@PathVariable Integer transactionID ){
        Transaction transaction = service.findById(transactionID).get();
        transaction.setConfirmTransfer(true);
        return ResponseEntity.ok("confirmation ok");
    }
    @ApiOperation(value = "user confirm receive ")
    @PostMapping(value="/transaction/{userId}/confirmTransference/{transactionID")
    public ResponseEntity<String> userConfirmReceive(@PathVariable Integer userId,@PathVariable Integer transactionID ){
        Transaction transaction = service.findById(transactionID).get();
        if(transaction.isConfirmTransfer()) {
            transaction.setConfirmReception(true);
        }
        else{
            System.out.println("error");
        }
        return ResponseEntity.ok("confirmation ok");
    }
}
