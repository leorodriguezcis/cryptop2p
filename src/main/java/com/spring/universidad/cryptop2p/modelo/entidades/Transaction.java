package com.spring.universidad.cryptop2p.modelo.entidades;


import com.spring.universidad.cryptop2p.modelo.entidades.numeradores.CryptoEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "crypto_type")
    @Enumerated(EnumType.STRING)
    public CryptoEnum cryptoType;
    @Column(name = "user_name")
    public Integer nominalValue;
    @Column(name = "cotization_value")
    public Integer valueCotization;
    @Column(name = "pesos_value")
    public Integer valuePesos;
    @Column(name = "operation user_number")
    public Integer operationUserNumber;
    @Column(name = "transaction_date")
    public LocalDateTime transactionDate;
    @Column(name = "transaction_type")
    public Boolean transactionType;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ID"))
    private User user;

    public Transaction(Boolean transactionType, LocalDateTime fecha, CryptoEnum cryptoName, Integer value, Integer cotization, Integer valuePesos, User user) {
        this.cryptoType = cryptoName;
        this.transactionDate = fecha;
        this.valuePesos = valuePesos;
        this.valueCotization = cotization;
        this.nominalValue = value;
        this.transactionType = transactionType;
        this.user = user;

    }



    public void setCryptoType(CryptoEnum cryptoType) {
        this.cryptoType = cryptoType;
    }
    public void setNominalValue(Integer nominalValue) {
        this.nominalValue = nominalValue;
    }
    public void setValueCotization(Integer valueCotization) {
        this.valueCotization = valueCotization;
    }
    public void setValuePesos(Integer valuePesos) {
        this.valuePesos = valuePesos;
    }
    public void setOperationUserNumber(Integer operationUserNumber) {
        this.operationUserNumber = operationUserNumber;
    }
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    public CryptoEnum getCryptoType() {
        return cryptoType;
    }
    public Integer getNominalValue() {
        return nominalValue;
    }
    public Integer getValueCotization() {
        return valueCotization;
    }
    public Integer getValuePesos() {
        return valuePesos;
    }
    public Integer getOperationUserNumber() {
        return operationUserNumber;
    }
}
