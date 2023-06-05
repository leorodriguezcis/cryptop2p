package com.spring.universidad.cryptop2p.services.interfaces;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

public interface TransactionDAO extends GenericDAO<Transaction>{
    String addTransaction(TransactionDTO transactionDTO, int user_id);
    Map<String, Object> transactionByCryptoName(CryptoEnum crypto);
    Map<String, Object> transactionsActive();

    Iterable<Transaction> searchByRangeActivity(LocalDateTime startDate, LocalDateTime endDate);

    String buyAnIntention(Integer userId, Integer transactionID);


    String sellAnIntention(Integer userId, Integer transactionID);

    Map<String, Object> confirmTransference(Integer userId, Integer transactionID, String action);

    Map<String, Object> cancelTransaction(Integer userId, Integer transactionID);

    Map<String, Object> BuscarTransaccion(Integer transactionId);
}
