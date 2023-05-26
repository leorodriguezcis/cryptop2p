package com.spring.universidad.cryptop2p.modelo.entities.controller;

import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
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
import org.springframework.validation.BindingResult;
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
    @PostMapping(value="/transaction/{userId}/buy/{transactionID}")
    public ResponseEntity<String> userBuyIntention(@PathVariable Integer userId,@PathVariable Integer transactionID ){
        Transaction transaction = service.findById(transactionID).get();
        transaction.setOtherUserId(userId);
        transaction.setIsActive(TransactionState.ON_PROCESS);
        service.save(transaction);
        User user = userDAO.findById(userId).get();
        return ResponseEntity.ok(user.getWallet());
    }
    @ApiOperation(value = "user confirm transference ")
    @PostMapping(value="/transaction/{userId}/confirmTransference/{transactionID}")
    public ResponseEntity<String> userConfirmTransference(@PathVariable Integer userId,@PathVariable Integer transactionID ){
        Transaction transaction = service.findById(transactionID).get();
        transaction.setConfirmTransfer(true);
        service.save(transaction);
        return ResponseEntity.ok("confirmation ok");
    }
    @ApiOperation(value = "user confirm receive ")
    @PostMapping(value="/transaction/{userId}/confirmReceive/{transactionID}")
    public ResponseEntity<Map<String, Object>> userConfirmReceive(@PathVariable Integer userId,@PathVariable Integer transactionID){
        Map<String, Object> message = new HashMap<>();
        Optional<Transaction> transactionO = service.findById(transactionID);
        if (transactionO.isEmpty()) {
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put("message", String.format("no existe ninguna transaccion con id: %s", transactionID));
            return ResponseEntity.badRequest().body(message);
        }

        Transaction transRes = transactionO.get();
        if(!transRes.isConfirmTransfer()){
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put("message", String.format("no puede confirmar la transaccion con id: %s ya que el usuario no confirmo la transferencia", transactionID));
            return ResponseEntity.badRequest().body(message);
        }
        if(userId.equals(transRes.getUser().getId())){
            transRes.setConfirmReception(true);
        }
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put("datos", transRes);
        service.save(transRes);
        return ResponseEntity.ok(message);
    }
    @ApiOperation(value = "user cancel transaction ")
    @PostMapping(value="/transaction/{userId}/cancel/{transactionID}")
    public ResponseEntity<Map<String, Object>> userCancelTransaction(@PathVariable Integer userId,@PathVariable Integer transactionID){
        Optional<Transaction> transactionO = service.findById(transactionID);
        Map<String, Object> message = new HashMap<>();
        Transaction transRes = transactionO.get();
        User user = userDAO.findById(userId).get();
        if(userId.equals(transRes.getOtherUserId()) || userId.equals(transRes.getUser().getId())){
            transRes.setUser(null);
            transRes.setCrypto(null);
            service.save(transRes);
            service.deleteById(transactionID);
            user.cancelTransaction();
            userDAO.save(user);
            message.put(MSG_SUCCESS, Boolean.TRUE);
            message.put("Se elimino la transaccion con id:", transactionID);
            return ResponseEntity.ok(message);
        }
        else{
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put("El usuario no pertenece a esta transaccion", userId);
            return ResponseEntity.ok(message);
        }
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
