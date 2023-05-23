package com.spring.universidad.cryptop2p.modelo.entities.dto;


import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.services.implementation.CryptoDAOimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CryptoDTO {
    private LocalDateTime hourCotization;
    private String symbol;
    private Double price ;
    private Double priceArs;
    private CryptoEnum SymbolToEnum;


    public CryptoEnum getSymbolToEnum() {
        return SymbolToEnum;
    }

    public void setSymbolToEnum(CryptoEnum symbolToEnum) {
        SymbolToEnum = symbolToEnum;
    }

    public LocalDateTime getHourCotization() {
        return hourCotization;
    }

    public void setHourCotization(LocalDateTime hourCotization) {
        this.hourCotization = hourCotization;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceArs() {
        return priceArs;
    }

    public void setPriceArs(Double priceArs) {
        this.priceArs = priceArs;
    }

    public CryptoDTO(){}
}
