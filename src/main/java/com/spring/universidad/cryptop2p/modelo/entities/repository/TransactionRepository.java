package com.spring.universidad.cryptop2p.modelo.entities.repository;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository("transactionRepository")
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    @Query("select t from Transaction t where t.cryptoType = ?1")
    Iterable<Transaction> transactionByCryptoName(CryptoEnum cryptoType);
    @Query("select t from Transaction t where t.isActive = true")
    Iterable<Transaction> transactionsActive();
    @Query("select t from Transaction t where t.isActive = false and t.confirmTransfer = true and t.confirmReception = true and t.transactionDate between ?1 and ?2")
    Iterable<Transaction> searchByRangeActivity(LocalDateTime start, LocalDateTime end);
}
