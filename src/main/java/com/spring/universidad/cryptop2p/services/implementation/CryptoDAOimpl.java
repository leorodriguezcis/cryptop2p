package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.modelo.entidades.dto.CryptoDTO;
import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.modelo.entities.repository.CryptoRepository;
import com.spring.universidad.cryptop2p.modelo.entities.repository.UserRepository;
import com.spring.universidad.cryptop2p.services.interfaces.CryptoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CryptoDAOimpl extends GenericDAOImpl<Crypto, CryptoRepository> implements CryptoDAO {
    @Autowired
    public  CryptoDAOimpl (CryptoRepository repo) {super(repo);
    }
    @Transactional
    public Crypto createCrypto(CryptoDTO cryptoDTO){
        Crypto crypto = new Crypto();
        crypto.setDate(cryptoDTO.getDate());
        crypto.setName(cryptoDTO.getName());
        crypto.setValue(crypto.getValue());
        repo.save(crypto);
        return  crypto;
    }

    @Override
    public User inicializerCrypto(CryptoEnum cryptoEnum) {
        return null;
    }
}