package com.spring.universidad.cryptop2p.services.interfaces;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface TransactionDAO extends GenericDAO<Transaction>{
    String addTransaction(TransactionDTO transactionDTO, int user_id);
    Iterable<Transaction> transactionByCryptoName(CryptoEnum crypto);

    @Transactional(readOnly = true)
    Iterable<Transaction> transactionsActive();

    Transaction newSellIntention(User user, TransactionDTO transactionDTO);

    Iterable<Transaction> searchByRangeActivity(LocalDateTime startDate, LocalDateTime endDate);
}
