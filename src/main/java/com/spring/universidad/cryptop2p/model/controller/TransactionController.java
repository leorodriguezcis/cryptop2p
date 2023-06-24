package com.spring.universidad.cryptop2p.model.controller;

import com.spring.universidad.cryptop2p.model.entities.Transaction;
import com.spring.universidad.cryptop2p.model.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.model.config.JWTUtil;
import com.spring.universidad.cryptop2p.model.dto.DateRangeDTO;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import com.spring.universidad.cryptop2p.services.interfaces.TransactionDAO;
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
    private static final  String MSG_SUCCESS = "SUCCESS";
    @Autowired
    public TransactionController(TransactionDAO service ) {
        super(service);
    }
    @Autowired
    private JWTUtil jwtUtil;

    @ApiOperation(value = "get by cryptoName")
    @GetMapping(value="/transaction/{crypto}")
    public ResponseEntity<Map<String, Object>> searchTransactionByCrypto( @PathVariable CryptoEnum crypto, @RequestHeader("Authorization") String token){
        return verifyMessageAndToken(token,service.transactionByCryptoName(crypto));
    }

    @ApiOperation(value = "get by cryptoActive")
    @GetMapping(value="/transaction/getActives")
    public ResponseEntity<Map<String, Object>> searchTransactionsActive( @RequestHeader("Authorization") String token){
        return verifyMessageAndToken(token,service.transactionsActive());
    }

    @ApiOperation(value = "get transaction by id")
    @GetMapping(value="/transaction/get/{transactionId}")
    public ResponseEntity<Map<String, Object>> searchTransaction(@PathVariable Integer transactionId,@RequestHeader("Authorization") String token){
        return verifyMessageAndToken(token,service.findTransaction(transactionId));
    }

    @ApiOperation(value = "user intention activity ")
    @PostMapping(value="/transaction/{userId}/intention")
    public ResponseEntity<Map<String, Object>> userSellIntention(@PathVariable Integer userId,@Valid @RequestBody TransactionDTO transaction, @RequestHeader("Authorization") String token){
        return verifyMessageAndToken(token,service.addTransaction(transaction,userId));
    }

    @ApiOperation(value = "user buy/send an activity ")
    @PostMapping(value="/transaction/{userId}/{intention}/{transactionID}")
    public ResponseEntity<Map<String, Object>> userBuyAnIntention(@PathVariable Integer userId,@PathVariable Integer transactionID,@PathVariable String intention, @RequestHeader("Authorization") String token){
        Map<String, Object> message = intention.equals("buy")? service.buyAnIntention(userId, transactionID):service.sellAnIntention(userId, transactionID);
        return verifyMessageAndToken(token,message);
    }
    @ApiOperation(value = "user confirm transference ")
    @PostMapping(value="/transaction/{userId}/confirm/{action}/{transactionID}")
    public ResponseEntity<Map<String, Object>> userConfirmTransference(@PathVariable Integer userId,@PathVariable Integer transactionID,@PathVariable String action, @RequestHeader("Authorization") String token){
        return verifyMessageAndToken(token,service.confirmTransference(userId, transactionID,action));
    }


    @ApiOperation(value = "user cancel transaction ")
    @PostMapping(value="/transaction/{userId}/cancel/{transactionID}")
    public ResponseEntity<Map<String, Object>> userCancelTransaction(@PathVariable Integer userId,@PathVariable Integer transactionID, @RequestHeader("Authorization") String token){
        return verifyMessageAndToken(token,service.cancelTransaction(userId,transactionID));
    }

    @ApiOperation(value = "List activity betwen 2 dates")
    @GetMapping(value="/transaction/get/range/toUser/{userId}")
    public ResponseEntity<Map<String, Object>> getTransactionByDates(@Valid @RequestBody DateRangeDTO rangeDTO, @PathVariable Integer userId, @RequestHeader("Authorization") String token){
        return verifyMessageAndToken(token,service.searchByRangeActivity(rangeDTO, userId));
    }

    private ResponseEntity<Map<String, Object>> verifyMessageAndToken(String token, Map<String, Object> message){
        if(message.get(MSG_SUCCESS).equals(Boolean.FALSE))
            return ResponseEntity.badRequest().body(message);
        if(!jwtUtil.validateToken(token)) {
            message.clear();
            message.put("ERROR","Token invalido");
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }
}
