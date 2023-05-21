package com.spring.universidad.cryptop2p.modelo.entities.dto;

import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Integer id;
    public CryptoEnum cryptoType;
    public Integer nominalValue;
    public BigDecimal valueCotization;
    public Integer valuePesos;
    public Integer operationUserNumber;
    public TransactionDTO(Boolean transactionType, CryptoEnum cryptoName, Integer value, Integer valuePesos, User user, BigDecimal cotization) {
        this.cryptoType = cryptoName;
        this.transactionDate = LocalDateTime.now();
        this.valuePesos = valuePesos;
        this.nominalValue = value;
        this.transactionType = transactionType;
        this.user = user;
        this.valueCotization = cotization;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CryptoEnum getCryptoType() {
        return cryptoType;
    }

    public void setCryptoType(CryptoEnum cryptoType) {
        this.cryptoType = cryptoType;
    }

    public Integer getNominalValue() {
        return nominalValue;
    }

    public void setNominalValue(Integer nominalValue) {
        this.nominalValue = nominalValue;
    }

    public BigDecimal getValueCotization() {
        return valueCotization;
    }

    public void setValueCotization(BigDecimal valueCotization) {
        this.valueCotization = valueCotization;
    }

    public Integer getValuePesos() {
        return valuePesos;
    }

    public void setValuePesos(Integer valuePesos) {
        this.valuePesos = valuePesos;
    }

    public Integer getOperationUserNumber() {
        return operationUserNumber;
    }

    public void setOperationUserNumber(Integer operationUserNumber) {
        this.operationUserNumber = operationUserNumber;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Boolean getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Boolean transactionType) {
        this.transactionType = transactionType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime transactionDate;
    public Boolean transactionType;
    public User user;

}
