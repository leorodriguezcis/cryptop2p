package com.spring.universidad.cryptop2p.modelo.entidades;

import com.spring.universidad.cryptop2p.modelo.entidades.numeradores.CryptoEnum;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_name")
    protected String name;
    @Column(name = "user_lastname")
    protected String lastname;
    @Column(name = "user_email")
    protected String email;
    @Column(name = "user_address")
    protected String address;
    @Column(name = "user_password")
    protected String password;
    @Column(name = "user_cvu")
    protected Integer cvu;
    @Column(name = "user_wallet")
    protected String wallet;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    public Set<Transaction> listTransactions;
    @Column(name = "user_reputation")
    public Integer reputation;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    public Set<Transaction> transactions;

    public User( String name, String lastname, String email, String address, String password, Integer cvu, String wallet ){
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.wallet = wallet;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public Integer getCvu() {
        return cvu;
    }

    public String getWallet() {
        return wallet;
    }

}
