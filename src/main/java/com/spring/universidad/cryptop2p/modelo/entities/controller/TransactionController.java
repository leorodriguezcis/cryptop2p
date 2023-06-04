package com.spring.universidad.cryptop2p.modelo.entities.controller;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.DateRangeDTO;
import com.spring.universidad.cryptop2p.modelo.entities.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.TransactionState;
import com.spring.universidad.cryptop2p.services.interfaces.CryptoDAO;
import com.spring.universidad.cryptop2p.services.interfaces.TransactionDAO;
import com.spring.universidad.cryptop2p.services.interfaces.UserDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@Api(tags = "Transaction")
public class TransactionController extends GenericController<Transaction, TransactionDAO>{
    private final CryptoDAO cryptoDAO;
    private final UserDAO userDAO;
    private static final  String MSG_SUCCESS = "SUCCESS";
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

    @ApiOperation(value = "user intention activity ")
    @PostMapping(value="/transaction/{userId}/intention")
    public ResponseEntity<String> userSellIntention(@PathVariable Integer userId,@Valid @RequestBody TransactionDTO transaction  ){
        String userTransferData = service.addTransaction(transaction,userId);
        return ResponseEntity.ok(userTransferData);
    }

    @ApiOperation(value = "user buy an activity ")
    @PostMapping(value="/transaction/{userId}/{intention}/{transactionID}")
    public ResponseEntity<String> userBuyAnIntention(@PathVariable Integer userId,@PathVariable Integer transactionID,@PathVariable String intention ){
        String userTransferData = "";
        if(intention=="buy") {
            userTransferData = service.buyAnIntention(userId, transactionID);
        }
        if(intention=="sell"){
            userTransferData = service.sellAnIntention(userId, transactionID);
        }
        return ResponseEntity.ok(userTransferData);
    }
    @ApiOperation(value = "user confirm transference ")
    @PostMapping(value="/transaction/{userId}/confirm/{action}/{transactionID}")
    public ResponseEntity<Map<String, Object>> userConfirmTransference(@PathVariable Integer userId,@PathVariable Integer transactionID,@PathVariable String action ){
        Map<String, Object> message  = service.confirmTransference(userId, transactionID,action);
        if(message.get("SUCCESS").equals(Boolean.FALSE)){
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }


    @ApiOperation(value = "user cancel transaction ")
    @PostMapping(value="/transaction/{userId}/cancel/{transactionID}")
    public ResponseEntity<Map<String, Object>> userCancelTransaction(@PathVariable Integer userId,@PathVariable Integer transactionID){
        Map<String, Object> message = service.cancelTransaction(userId,transactionID);
        if(message.get("SUCCESS").equals(Boolean.FALSE)){
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }

    @ApiOperation(value = "List activity betwen 2 dates")
    @GetMapping(value="/transaction/get/range")
    public ResponseEntity<Map<String, Object>> getTransactionByDates(@Valid @RequestBody DateRangeDTO rangeDTO){
        Map<String, Object> message = new HashMap<>();
        Iterable<Transaction> transactionRange = service.searchByRangeActivity(convertToLocalDateTime(rangeDTO.getStartDate()), convertToLocalDateTime(rangeDTO.getEndDate()));
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put("datos", transactionRange);
        return ResponseEntity.ok(message);
    }

    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return LocalDateTime.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }
}
