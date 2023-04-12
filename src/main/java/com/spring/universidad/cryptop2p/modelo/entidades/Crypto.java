package com.spring.universidad.cryptop2p.modelo.entidades;

import com.spring.universidad.cryptop2p.modelo.entidades.numeradores.CryptoEnum;
import services.BinanceService;

import java.io.Serializable;
import java.time.LocalDate;

public class Crypto implements Serializable {

    public CryptoEnum name;
    public LocalDate date;
    public Integer value;
    public BinanceService binanceService;
    public Crypto(BinanceService binanceService){
        this.binanceService = binanceService;
    }
    public void getInfo(CryptoEnum nameCrypto){
        binanceService.getPrice(nameCrypto);
    }
}
