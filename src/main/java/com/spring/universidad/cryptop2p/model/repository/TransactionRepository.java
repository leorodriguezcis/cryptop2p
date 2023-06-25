package com.spring.universidad.cryptop2p.model.repository;

import com.spring.universidad.cryptop2p.model.entities.Transaction;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository("transactionRepository")
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    @Query("select t from Transaction t where t.crypto.name = ?1")
    Iterable<Transaction> transactionByCryptoName(CryptoEnum cryptoType);
    @Query("select t from Transaction t where t.transactionState = 'NEW'")
    Iterable<Transaction> transactionsActive();
    @Query("select t from Transaction t where t.user.id = ?3 and t.transactionDate BETWEEN ?1 and ?2 and t.transactionState = 'FINISHED'")
    Iterable<Transaction> searchByRangeActivity(LocalDateTime start,LocalDateTime end,Integer userId);
}
