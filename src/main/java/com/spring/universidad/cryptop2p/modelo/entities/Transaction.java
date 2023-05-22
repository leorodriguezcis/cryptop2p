package com.spring.universidad.cryptop2p.modelo.entities;



import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
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
    public BigDecimal valueCotization;
    @Column(name = "pesos_value")
    public Integer valuePesos;
    @Column(name = "operation_user_number")
    public Integer operationUserNumber;
    @Column(name = "transaction_date")
    public LocalDateTime transactionDate;
    @Column(name = "transaction_type")
    public Boolean transactionType;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ID"))
    public User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "crypto_id", foreignKey = @ForeignKey(name = "FK_CRYPTO_ID"))
    private Crypto crypto;

    public Transaction() {

    }

    public Crypto getCrypto() {
        return crypto;
    }

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public Transaction(Boolean transactionType, Crypto crypto, Integer value, Integer valuePesos, User user, BigDecimal cotization) {
        this.crypto = crypto;
        this.transactionDate = LocalDateTime.now();
        this.valuePesos = valuePesos;
        this.nominalValue = value;
        this.transactionType = transactionType;
        this.user = user;
        this.valueCotization = cotization;
    }



    public void setCryptoType(CryptoEnum cryptoType) {
        this.cryptoType = cryptoType;
    }
    public void setNominalValue(Integer nominalValue) {
        this.nominalValue = nominalValue;
    }
    public void setValueCotization(BigDecimal valueCotization) {
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
    public BigDecimal getValueCotization() {
        return valueCotization;
    }
    public Integer getValuePesos() {
        return valuePesos;
    }
    public Integer getOperationUserNumber() {
        return operationUserNumber;
    }
}
