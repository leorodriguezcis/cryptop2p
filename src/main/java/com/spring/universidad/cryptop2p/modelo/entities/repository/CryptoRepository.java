package com.spring.universidad.cryptop2p.modelo.entities.repository;

import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("cryptoRepository")
public interface CryptoRepository extends CrudRepository<Crypto, Integer> {
}
