package com.spring.universidad.cryptop2p.services.interfaces;


import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CryptoDAO extends GenericDAO<Crypto>{
    Boolean inicializerCrypto(CryptoEnum[] cryptoEnum);

    @Transactional(readOnly = true)
    Optional<Crypto> findCryptosByName(CryptoEnum nombre);
}
