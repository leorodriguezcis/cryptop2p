package com.spring.universidad.cryptop2p.services.interfaces;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionDAO extends GenericDAO<Transaction>{
    Transaction addTransaction(TransactionDTO transactionDTO);
    Iterable<Transaction> transactionByCryptoName(CryptoEnum crypto);

    @Transactional(readOnly = true)
    Iterable<Transaction> transactionsActive();
}
