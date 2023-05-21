package com.spring.universidad.cryptop2p.modelo.entities.controller;

import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import com.spring.universidad.cryptop2p.modelo.entities.dto.CryptoDTO;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.services.interfaces.CryptoDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum.*;

@RestController
@Api(tags = "Crypto")
public class CryptoController extends GenericController<Crypto, CryptoDAO> {

    @Autowired
    public CryptoController(CryptoDAO service) {
        super(service);
    }

    @ApiOperation(value = "initialize crypto")
    @PostMapping(value="/initialize/crypto")
    public ResponseEntity<String> initializeCrypto(){
        CryptoEnum[] crypotoList = new CryptoEnum[15];
        crypotoList[0] = ALICEUSDT;
        crypotoList[1] = MATICUSDT;
        crypotoList[2] = AXSUSDT ;
        crypotoList[3] = AAVEUSDT;
        crypotoList[5] = ATOMUSDT ;
        crypotoList[6] = NEOUSDT;
        crypotoList[7] = DOTUSDT;
        crypotoList[8] = ETHUSDT;
        crypotoList[9] = CAKEUSDT;
        crypotoList[10] = BTCUSDT;
        crypotoList[11] = BNBUSDT;
        crypotoList[12] = ADAUSDT;
        crypotoList[13] = TRXUSDT ;
        crypotoList[14] = AUDIOUSDT;
        service.inicializerCrypto(crypotoList);
        return ResponseEntity.ok("hola");
    }

}
