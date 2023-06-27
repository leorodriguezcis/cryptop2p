package com.spring.universidad.cryptop2p.model.controller;

import com.spring.universidad.cryptop2p.model.config.JWTUtil;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import com.spring.universidad.cryptop2p.services.implementation.CryptoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
        service.inicializerCrypto();
        return ResponseEntity.ok("Cryptos inicializadas");
    }
    @ApiOperation(value = "get all cryptos values")
    @GetMapping(value="/crypto/getAll")
    public ResponseEntity<Map<String, Object>> searchTransactionsActive(@RequestHeader("Authorization") String token){
        return verifyMessageAndToken(token, service.getAllCryptoCotization());
    }
    @ApiOperation(value = "get crypto value for 24hs")
    @GetMapping(value="/crypto/get24h/{symbol}")
    public  ResponseEntity<Map<String, Object>> getCrypto24h(@PathVariable CryptoEnum symbol){
        return  ResponseEntity.ok((service.getCotizationBySymbol24hs(symbol)));
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
