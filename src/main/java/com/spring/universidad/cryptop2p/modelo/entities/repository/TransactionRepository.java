package com.spring.universidad.cryptop2p.modelo.entities.repository;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository("transactionRepository")
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    @Query("select t from Transaction t where t.cryptoType = ?1")
    Iterable<Transaction> transactionByCryptoName(CryptoEnum cryptoType);
    @Query("select t from Transaction t where t.transactionState != 'FINISHED' AND t.transactionState != 'CANCELLED'")
    Iterable<Transaction> transactionsActive();
    @Query("select t from Transaction t where t.user.id = ?3 and t.transactionDate BETWEEN ?1 and ?2")
    Iterable<Transaction> searchByRangeActivity(LocalDateTime start,LocalDateTime end,Integer userId);
}
