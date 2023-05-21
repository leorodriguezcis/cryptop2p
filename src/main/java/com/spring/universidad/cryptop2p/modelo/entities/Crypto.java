package com.spring.universidad.cryptop2p.modelo.entities;

import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "crypto")
public class Crypto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    public CryptoEnum name;
    @Column(name = "date")
    public LocalDate date;

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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Column(name = "value")
    public Double value;
    @Column(name = "valueInArs")
    public Double valueInArs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    private Transaction transaction;
    public Crypto(){

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
