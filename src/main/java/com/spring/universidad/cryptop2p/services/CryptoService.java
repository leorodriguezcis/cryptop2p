package com.spring.universidad.cryptop2p.services;

import com.spring.universidad.cryptop2p.modelo.entidades.dto.CryptoDTO;
import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import com.spring.universidad.cryptop2p.modelo.entities.repository.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Deprecated
@Service
@Transactional
public class CryptoService {
    @Autowired
    private CryptoRepository cryptoRepository;
    public  CryptoService (CryptoRepository cryptoRepository){
        this.cryptoRepository = cryptoRepository;
    }

    public Crypto createCrypto(CryptoDTO cryptoDTO){
        Crypto crypto = new Crypto();
        crypto.setDate(cryptoDTO.getDate());
        crypto.setName(cryptoDTO.getName());
        crypto.setValue(crypto.getValue());
        cryptoRepository.save(crypto);
        return  crypto;
    }
}
