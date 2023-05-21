package com.spring.universidad.cryptop2p.services.interfaces;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.dto.TransactionDTO;

public interface TransactionDAO extends GenericDAO<Transaction>{
    Transaction addTransaction(TransactionDTO transactionDTO);
}
