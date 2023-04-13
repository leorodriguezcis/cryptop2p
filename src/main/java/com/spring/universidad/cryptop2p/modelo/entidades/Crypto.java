package com.spring.universidad.cryptop2p.modelo.entidades;

import com.spring.universidad.cryptop2p.modelo.entidades.numeradores.CryptoEnum;
import org.springframework.web.client.RestTemplate;
import services.BinanceService;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Crypto implements Serializable {

    public CryptoEnum name;
    public LocalDate date;
    public Integer value;
    private RestTemplate rTemplate = new RestTemplate();
    private BinanceService binanceService = new BinanceService(rTemplate);
    public Crypto(){

    }
    public BigDecimal getInfo(CryptoEnum nameCrypto){
       return binanceService.getPrice(nameCrypto);
    }
}
