package com.spring.universidad.cryptop2p.modelo.entities.dto;

import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.TransactionState;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Integer id;
    public CryptoEnum cryptoType;
    public Double nominalValue;
    public BigDecimal valueCotization;
    public Double valuePesos;
    public Integer operationUserNumber;
    public TransactionState state;
    public LocalDateTime transactionDate;
    public String transactionType;

    public String user;

    public TransactionDTO(String transactionType, CryptoEnum cryptoName, Double value, Double valuePesos, String user, BigDecimal cotization) {
        this.cryptoType = cryptoName;
        this.transactionDate = LocalDateTime.now();
        this.valuePesos = valuePesos;
        this.nominalValue = value;
        this.transactionType = transactionType;
        this.user = user;
        this.valueCotization = cotization;
        this.state = TransactionState.NEW;
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

    public Double getNominalValue() {
        return nominalValue;
    }

    public void setNominalValue(Double nominalValue) {
        this.nominalValue = nominalValue;
    }

    public BigDecimal getValueCotization() {
        return valueCotization;
    }

    public void setValueCotization(BigDecimal valueCotization) {
        this.valueCotization = valueCotization;
    }

    public Double getValuePesos() {
        return valuePesos;
    }

    public void setValuePesos(Double valuePesos) {
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

}
