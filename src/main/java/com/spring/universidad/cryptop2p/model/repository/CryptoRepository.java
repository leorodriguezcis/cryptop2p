package com.spring.universidad.cryptop2p.model.repository;

import com.spring.universidad.cryptop2p.model.entities.Crypto;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("cryptoRepository")
public interface CryptoRepository extends CrudRepository<Crypto, Integer> {
    Optional<Crypto> findCryptosByName(CryptoEnum nombre);
}
