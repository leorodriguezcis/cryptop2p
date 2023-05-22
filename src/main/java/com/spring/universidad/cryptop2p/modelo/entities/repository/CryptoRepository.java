package com.spring.universidad.cryptop2p.modelo.entities.repository;

import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("cryptoRepository")
public interface CryptoRepository extends CrudRepository<Crypto, Integer> {
    Optional<Crypto> findCryptoByName(CryptoEnum nombre);
}
