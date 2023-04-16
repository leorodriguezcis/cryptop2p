package com.spring.universidad.cryptop2p.modelo.entidades;

import com.spring.universidad.cryptop2p.modelo.entidades.numeradores.CryptoEnum;
import org.springframework.web.client.RestTemplate;
import services.BinanceService;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
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
    private RestTemplate rTemplate = new RestTemplate();
    private BinanceService binanceService = new BinanceService(rTemplate);
    public Crypto(){

    }
    public BigDecimal getInfo(CryptoEnum nameCrypto){
       return binanceService.getPrice(nameCrypto);
    }
}
