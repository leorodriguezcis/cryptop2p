package com.spring.universidad.cryptop2p.modelo.entidades.repository;

import com.spring.universidad.cryptop2p.modelo.entidades.Crypto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("cryptoRepository")
public interface CryptoRepository extends CrudRepository<Crypto, Integer> {
}
