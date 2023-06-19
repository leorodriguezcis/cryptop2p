package com.spring.universidad.cryptop2p.model.controller;

import com.spring.universidad.cryptop2p.model.entities.Crypto;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import com.spring.universidad.cryptop2p.services.interfaces.CryptoDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.spring.universidad.cryptop2p.model.enums.CryptoEnum.*;

@RestController
@Api(tags = "Crypto")
public class CryptoController extends GenericController<Crypto, CryptoDAO> {

    @Autowired
    public CryptoController(CryptoDAO service) {
        super(service);
    }

    @ApiOperation(value = "initialize crypto")
    @PostMapping(value="/crypto/initialize")
    public ResponseEntity<String> initializeCrypto(){
        CryptoEnum[] crypotoList = new CryptoEnum[14];
        crypotoList[0] = ALICEUSDT;
        crypotoList[1] = MATICUSDT;
        crypotoList[2] = AXSUSDT ;
        crypotoList[3] = AAVEUSDT;
        crypotoList[4] = ATOMUSDT ;
        crypotoList[5] = NEOUSDT;
        crypotoList[6] = DOTUSDT;
        crypotoList[7] = ETHUSDT;
        crypotoList[8] = CAKEUSDT;
        crypotoList[9] = BTCUSDT;
        crypotoList[10] = BNBUSDT;
        crypotoList[11] = ADAUSDT;
        crypotoList[12] = TRXUSDT ;
        crypotoList[13] = AUDIOUSDT;
        service.inicializerCrypto(crypotoList);
        return ResponseEntity.ok("Cryptos inicializadas");
    }
    @ApiOperation(value = "get all cryptos values")
    @GetMapping(value="/crypto/getAll")
    public ResponseEntity<Map<String, Object>> searchTransactionsActive(){
        Map<String, Object> message = service.getAllCryptoCotization();
        return ResponseEntity.ok(message);
    }

}