package com.spring.universidad.cryptop2p.modelo.entidades.controller;

import com.spring.universidad.cryptop2p.modelo.entidades.Crypto;
import com.spring.universidad.cryptop2p.services.CryptoService;
import com.spring.universidad.cryptop2p.services.interfaces.CryptoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoController extends GenericController<Crypto, CryptoDAO>{

    @Autowired
    private CryptoService userService;

    public CryptoController(CryptoDAO service) {
        super(service);
    }
}
