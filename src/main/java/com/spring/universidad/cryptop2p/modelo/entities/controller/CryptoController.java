package com.spring.universidad.cryptop2p.modelo.entities.controller;

import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import com.spring.universidad.cryptop2p.services.interfaces.CryptoDAO;
import com.spring.universidad.cryptop2p.modelo.entidades.dto.CryptoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "Initialize")
public class CryptoController extends GenericController<Crypto, CryptoDAO> {

    @Autowired
    public CryptoController(CryptoDAO service) {
        super(service);
    }

    @ApiOperation(value = "initialize crypto")
    @PostMapping(value="/initialize")
    public ResponseEntity<String> userRegister(@Valid @RequestBody CryptoDTO crypto){
        return ResponseEntity.ok("hola");
    }

}
