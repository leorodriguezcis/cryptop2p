package com.spring.universidad.cryptop2p.services.interfaces;

import com.spring.universidad.cryptop2p.model.entities.Transaction;
import com.spring.universidad.cryptop2p.model.dto.DateRangeDTO;
import com.spring.universidad.cryptop2p.model.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import java.util.Map;

public interface TransactionDAO extends GenericDAO<Transaction>{
    Map<String, Object> addTransaction(TransactionDTO transactionDTO, int userId);

    Map<String, Object> transactionByCryptoName(CryptoEnum crypto);

    Map<String, Object> transactionsActive();

    Map<String, Object> searchByRangeActivity(DateRangeDTO dateRangeDTO, Integer userID);

    Map<String, Object> publicAnIntention(Integer userId, Integer transactionID, String intention);

    Map<String, Object> confirmTransference(Integer userId, Integer transactionID, String action);

    Map<String, Object> cancelTransaction(Integer userId, Integer transactionID);

    Map<String, Object> findTransaction(Integer transactionId);
}
