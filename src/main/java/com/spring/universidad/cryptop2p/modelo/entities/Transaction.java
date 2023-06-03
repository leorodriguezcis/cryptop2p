package com.spring.universidad.cryptop2p.modelo.entities;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.TransactionState;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
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
    @Positive
    @Min(value = 1, message = "nominal value should not be less than 1")
    @Max(value = 99999999, message = "nominal value should not be greater than 99999999")
    public Integer nominalValue;
    @Column(name = "cotization_value")
    @Positive
    public BigDecimal valueCotization;
    @Column(name = "pesos_value")
    @Positive
    public Integer valuePesos;
    @Column(name = "operation_user_number")
    @Positive
    public Integer operationUserNumber;
    @Column(name = "transaction_date")
    public LocalDateTime transactionDate;
    @Column(name = "transaction_type")
    public String transactionType;
    @Column(name = "state_of_transaction")
    @Enumerated(EnumType.STRING)
    public TransactionState transactionState;
    @Column(name = "transaction_other_user_id")
    public Integer otherUserId;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "transactions"})
    public User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "crypto_id", foreignKey = @ForeignKey(name = "FK_CRYPTO_ID"))
    private Crypto crypto;

    public Transaction() {

    }
    public Transaction(String transactionType, Crypto crypto, Integer value, Integer valuePesos, BigDecimal cotization) {

        this.crypto = crypto;
        this.transactionDate = LocalDateTime.now();
        this.valuePesos = valuePesos;
        this.nominalValue = value;
        this.transactionType = transactionType;
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionState getIsActive() {
        return this.transactionState;
    }

    public void setIsActive(TransactionState active) {
        this.transactionState = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(Integer otherUserId) {
        this.otherUserId = otherUserId;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }
}
