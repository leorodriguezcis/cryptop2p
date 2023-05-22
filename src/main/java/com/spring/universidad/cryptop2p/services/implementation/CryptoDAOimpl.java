package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import com.spring.universidad.cryptop2p.modelo.entities.dto.CryptoDTO;
import com.spring.universidad.cryptop2p.modelo.entities.dto.DolarDTOHelper;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.modelo.entities.repository.CryptoRepository;
import com.spring.universidad.cryptop2p.services.interfaces.CryptoDAO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

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
        System.out.println("name"+cryptoDTO.getSymbolToEnum().toString());
        repo.save(crypto);
        return  crypto;
    }
    @SneakyThrows
    public CryptoDTO getCotizationBySymbol(CryptoEnum symbol) {
        NumberFormat nf = NumberFormat.getInstance();
        Double dollarPrice = nf.parse(priceUsd()).doubleValue();
        LocalDateTime hour = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
        String url = "https://api1.binance.com/api/v3/ticker/price?symbol=" + symbol;
        CryptoDTO crypto = restTemplate.getForObject(url, CryptoDTO.class);
        Double priceCriptoInUsd = crypto.getPrice();
        Double priceArs = priceCriptoInUsd * dollarPrice;
        crypto.setPriceArs(priceArs);
        crypto.setHourCotization(hour);
        crypto.setSymbolToEnum(symbol);
        return crypto;
    }
    @Override

    public Boolean inicializerCrypto(CryptoEnum[] cryptoEnumList) {
        for(CryptoEnum cryptoEnum:cryptoEnumList){
            createCrypto(getCotizationBySymbol(cryptoEnum));
        }

        return true;
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Crypto> findCryptoByName(CryptoEnum nombre) {
        return repo.findCryptoByName(nombre);
    }

    public String priceUsd(){
        String url ="https://www.dolarsi.com/api/api.php?type=valoresprincipales";
		DolarDTOHelper[] casa = restTemplate.getForObject(url, DolarDTOHelper[].class);
       return casa[1].getCasa().getCompra();
    }
}
