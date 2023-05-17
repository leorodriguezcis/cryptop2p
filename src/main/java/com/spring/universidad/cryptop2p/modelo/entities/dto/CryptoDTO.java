package com.spring.universidad.cryptop2p.modelo.entidades.dto;


import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;

import java.time.LocalDate;

public class CryptoDTO {
    public CryptoEnum name;
    public LocalDate date;
    public Double value;

    public CryptoDTO(CryptoEnum name, LocalDate date, Double value) {
        this.name = name;
        this.date = date;
        this.value = value;
    }

    public CryptoEnum getName() {
        return name;
    }

    public void setName(CryptoEnum name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
