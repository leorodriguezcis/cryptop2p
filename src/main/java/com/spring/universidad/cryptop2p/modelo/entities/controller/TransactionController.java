package com.spring.universidad.cryptop2p.modelo.entities.controller;

import com.spring.universidad.cryptop2p.modelo.entities.CryptoActiveResult;
import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.dto.DateRangeDTO;
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
    public ResponseEntity<Map<String, Object>> searchTransactionByCrypto( @PathVariable CryptoEnum crypto){
        Map<String, Object> message = service.transactionByCryptoName(crypto);
        if(message.get("SUCCESS").equals(Boolean.FALSE)){
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }

    @ApiOperation(value = "get by cryptoActive")
    @GetMapping(value="/transaction/getActives")
    public ResponseEntity<Map<String, Object>> searchTransactionsActive(){
        Map<String, Object> message = service.transactionsActive();
        if(message.get("SUCCESS").equals(Boolean.FALSE)){
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }

    @ApiOperation(value = "get transaction by id")
    @GetMapping(value="/transaction/get/{transactionId}")
    public ResponseEntity<Map<String, Object>> searchTransaction(@PathVariable Integer transactionId){
        Map<String, Object> message = service.findTransaction(transactionId);
        if(message.get("SUCCESS").equals(Boolean.FALSE)){
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
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
        if(intention.equals("buy")) {
            userTransferData = service.buyAnIntention(userId, transactionID);
        }
        if(intention.equals("sell")) {
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
        if(message.get("SUCCESS").equals(Boolean.FALSE))
            return ResponseEntity.badRequest().body(message);

        return ResponseEntity.ok(message);
    }

    @ApiOperation(value = "List activity betwen 2 dates")
    @GetMapping(value="/transaction/get/range/toUser/{userId}")
    public ResponseEntity<Map<String, Object>> getTransactionByDates(@Valid @RequestBody DateRangeDTO rangeDTO, @PathVariable Integer userId){
        Map<String, Object> message = new HashMap<>();
        CryptoActiveResult transactionRange = service.searchByRangeActivity(rangeDTO, userId);
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put("datos", transactionRange);
        return ResponseEntity.ok(message);
    }


}
