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

    @Transactional
    public Crypto createCrypto(CryptoDTO cryptoDTO){
        Crypto crypto = new Crypto();
        crypto.setDate(cryptoDTO.getHourCotization().toLocalDate());
        crypto.setName(cryptoDTO.getSymbolToEnum());
        crypto.setValue(cryptoDTO.getPrice());
        crypto.setValueInArs(cryptoDTO.getPriceArs());
        repo.save(crypto);
        return  crypto;
    }
    public CryptoDTO getCotizationBySymbol(CryptoEnum symbol) {
        LocalDateTime hour = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
        String url = "https://api1.binance.com/api/v3/ticker/price?symbol=" + symbol;
        CryptoDTO crypto = restTemplate.getForObject(url, CryptoDTO.class);
        System.out.println("print test"+crypto.getPrice());
        System.out.println(crypto);
        Double priceCriptoInUsd = crypto.getPrice();
        Double priceArs = priceCriptoInUsd * 350;
        crypto.setPriceArs(priceArs);
        crypto.setHourCotization(hour);
        crypto.setSymbolToEnum(symbol);
return crypto;
    }
    @Override

    public Boolean inicializerCrypto(CryptoEnum[] cryptoEnumList) {
        for(CryptoEnum cryptoEnum:cryptoEnumList){
            System.out.println(cryptoEnum);
            createCrypto(getCotizationBySymbol(cryptoEnum));
        }

        return true;
    }

    public String priceUsd(){
        String url ="https://api-dolar-argentina.herokuapp.com/api/dolarblue";
        DolarDTO[] valorDolar = restTemplate.getForObject(url, DolarDTO[].class);
        return valorDolar[1].getCompra();
    }
}
