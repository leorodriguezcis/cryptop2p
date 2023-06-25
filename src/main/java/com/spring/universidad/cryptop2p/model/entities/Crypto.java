package com.spring.universidad.cryptop2p.model.entities;

import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "cryptos")
public class Crypto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    public CryptoEnum name;
    @Column(name = "date")
    @FutureOrPresent
    public LocalDate date;
    @Column(name = "value")
    public Double value;
    @Column(name = "valueInArs")
    public Double valueInArs;

    public Crypto(CryptoEnum name, LocalDate date, Double value, Double valueInArs) {
        this.name = name;
        this.date = date;
        this.value = value;
        this.valueInArs = valueInArs;
    }

    public Crypto(){
    //need a blank constructor
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
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValueInArs() {
        return valueInArs;
    }

    public void setValueInArs(Double valueInArs) {
        this.valueInArs = valueInArs;
    }
}
