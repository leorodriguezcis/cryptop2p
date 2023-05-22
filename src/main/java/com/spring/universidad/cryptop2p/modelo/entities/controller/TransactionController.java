package com.spring.universidad.cryptop2p.modelo.entities.controller;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.services.interfaces.TransactionDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "Transaction")
public class TransactionController extends GenericController<Transaction, TransactionDAO>{

    @Autowired
    public TransactionController(TransactionDAO service) {
        super(service);
    }

    @ApiOperation(value = "Transactions")
    @PostMapping(value="/transaction/new")
    public ResponseEntity<String> createTransaction(@Valid @RequestBody TransactionDTO transaction){
        service.addTransaction(transaction);
        return ResponseEntity.ok(transaction.getUser().getName());
    }
}
