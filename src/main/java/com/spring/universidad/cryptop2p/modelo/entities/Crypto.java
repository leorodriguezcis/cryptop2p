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
    @Column(name = "value")
    public Double value;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    private Transaction transaction;
    public Crypto(){
    //need a blank constructor
    }
}
