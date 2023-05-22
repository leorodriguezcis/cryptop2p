package com.spring.universidad.cryptop2p.modelo.entities.repository;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("transactionRepository")
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    @Query("select t from Transaction t where t.cryptoType = ?1")
    Iterable<Transaction> transactionByCryptoName(CryptoEnum cryptoType);
}
