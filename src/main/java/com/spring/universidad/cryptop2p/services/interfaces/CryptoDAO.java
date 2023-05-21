package com.spring.universidad.cryptop2p.services.interfaces;


import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.UserRegisterDto;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;

public interface CryptoDAO extends GenericDAO<Crypto>{
    Boolean inicializerCrypto(CryptoEnum[] cryptoEnum);
}
