package com.spring.universidad.cryptop2p.services.interfaces;


import com.spring.universidad.cryptop2p.model.entities.Crypto;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

public interface CryptoDAO extends GenericDAO<Crypto>{
    Boolean inicializerCrypto(CryptoEnum[] cryptoEnum);

    @Transactional(readOnly = true)
    Optional<Crypto> findCryptosByName(CryptoEnum nombre);

    Map<String, Object> getAllCryptoCotization();
}
