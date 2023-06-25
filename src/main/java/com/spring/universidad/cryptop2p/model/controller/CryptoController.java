package com.spring.universidad.cryptop2p.model.controller;

import com.spring.universidad.cryptop2p.model.config.JWTUtil;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import com.spring.universidad.cryptop2p.services.implementation.CryptoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.spring.universidad.cryptop2p.model.enums.CryptoEnum.*;

@RestController
@Api(tags = "Crypto")
public class CryptoController {
    private static final  String MSG_SUCCESS = "SUCCESS";
    @Autowired
    private  JWTUtil jwtUtil;
    @Autowired
    CryptoService service;

    @ApiOperation(value = "initialize crypto")
    @PostMapping(value="/crypto/initialize")
    public ResponseEntity<String> initializeCrypto(){
        CryptoEnum[] crypotoList = new CryptoEnum[13];
        crypotoList[0] = ALICEUSDT;
        crypotoList[1] = MATICUSDT;
        crypotoList[2] = AXSUSDT ;
        crypotoList[3] = AAVEUSDT;
        crypotoList[4] = ATOMUSDT ;
        crypotoList[5] = NEOUSDT;
        crypotoList[6] = DOTUSDT;
        crypotoList[7] = ETHUSDT;
        crypotoList[8] = BTCUSDT;
        crypotoList[9] = BNBUSDT;
        crypotoList[10] = ADAUSDT;
        crypotoList[11] = TRXUSDT ;
        crypotoList[12] = AUDIOUSDT;
        service.inicializerCrypto(crypotoList);
        return ResponseEntity.ok("Cryptos inicializadas");
    }
    @ApiOperation(value = "get all cryptos values")
    @GetMapping(value="/crypto/getAll")
    public ResponseEntity<Map<String, Object>> searchTransactionsActive(@RequestHeader("Authorization") String token){
        return verifyMessageAndToken(token, service.getAllCryptoCotization());
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
