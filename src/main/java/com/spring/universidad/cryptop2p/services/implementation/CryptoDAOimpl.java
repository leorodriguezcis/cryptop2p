package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import com.spring.universidad.cryptop2p.modelo.entities.dto.CryptoDTO;
import com.spring.universidad.cryptop2p.modelo.entities.dto.DolarDTO;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.modelo.entities.repository.CryptoRepository;
import com.spring.universidad.cryptop2p.services.interfaces.CryptoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class CryptoDAOimpl extends GenericDAOImpl<Crypto, CryptoRepository> implements CryptoDAO {
    private RestTemplate restTemplate= new RestTemplate();
    @Autowired
    public  CryptoDAOimpl (CryptoRepository repo) {super(repo);
    }

    public Crypto createCrypto(CryptoDTO cryptoDTO){
        Crypto crypto = new Crypto();
        crypto.setDate(cryptoDTO.getDate());
        crypto.setName(cryptoDTO.getName());
        crypto.setValue(crypto.getValue());
        repo.save(crypto);
        return  crypto;
    }
    public CryptoDTO getCotizationBySymbol(CryptoEnum symbol) {
        Double dollar = Double.parseDouble(priceUsd());
        LocalDateTime hour = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
        String url = "https://api1.binance.com/api/v3/ticker/price?symbol=" + symbol;
        CryptoDTO crypto = restTemplate.getForObject(url, CryptoDTO.class);
        Double priceCriptoInUsd = crypto.getValue();
        Double priceArs = priceCriptoInUsd * dollar;
        crypto.setValueInArs(priceArs);
        crypto.setDate(hour.toLocalDate());
        return crypto;
    }
    @Override
    @Transactional
    public Boolean inicializerCrypto(CryptoEnum[] cryptoEnumList) {
        for(CryptoEnum cryptoEnum:cryptoEnumList){
            repo.save(createCrypto(getCotizationBySymbol(cryptoEnum)));
        }

        return true;
    }

    public String priceUsd(){
        String url ="https://api-dolar-argentina.herokuapp.com/api/dolarblue";
        DolarDTO[] valorDolar = restTemplate.getForObject(url, DolarDTO[].class);
        return valorDolar[1].getCompra();
    }
}
