package com.spring.universidad.cryptop2p.modelo.entities;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    public String transactionType;
    @Column(name = "is_active")
    public boolean isACtive;
    @Column(name = "transaction_other_user_id")
    public Integer otherUserId;
    @Column(name = "confirm_transfer")
    public boolean confirmTransfer = false;
    @Column(name = "confirm_reception")
    public boolean confirmReception = false;

    public boolean isConfirmTransfer() {
        return confirmTransfer;
    }

    public void setConfirmTransfer(boolean confirmTransfer) {
        this.confirmTransfer = confirmTransfer;
    }

    public boolean isConfirmReception() {
        return confirmReception;
    }

    public void setConfirmReception(boolean confirmReception) {
        this.confirmReception = confirmReception;
    }



    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
    }

    @Column(name = "transaction_isActive")
    public boolean isActive;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "transactions"})
    public User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "crypto_id", foreignKey = @ForeignKey(name = "FK_CRYPTO_ID"))
    private Crypto crypto;

    public Transaction() {

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
}
