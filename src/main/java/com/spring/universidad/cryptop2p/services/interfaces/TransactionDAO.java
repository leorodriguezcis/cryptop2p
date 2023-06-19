package com.spring.universidad.cryptop2p.services.interfaces;

import com.spring.universidad.cryptop2p.model.response.CryptoActiveResult;
import com.spring.universidad.cryptop2p.model.entities.Transaction;
import com.spring.universidad.cryptop2p.model.dto.DateRangeDTO;
import com.spring.universidad.cryptop2p.model.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;

import java.util.Map;

public interface TransactionDAO extends GenericDAO<Transaction>{
    String addTransaction(TransactionDTO transactionDTO, int user_id);
    Map<String, Object> transactionByCryptoName(CryptoEnum crypto);
    Map<String, Object> transactionsActive();

    CryptoActiveResult searchByRangeActivity(DateRangeDTO dateRangeDTO, Integer userID);

    String buyAnIntention(Integer userId, Integer transactionID);


    String sellAnIntention(Integer userId, Integer transactionID);

    Map<String, Object> confirmTransference(Integer userId, Integer transactionID, String action);

    Map<String, Object> cancelTransaction(Integer userId, Integer transactionID);

    Map<String, Object> findTransaction(Integer transactionId);
}
