package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.modelo.entities.repository.TransactionRepository;
import com.spring.universidad.cryptop2p.modelo.entities.repository.UserRepository;
import com.spring.universidad.cryptop2p.services.interfaces.TransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionDAOImpl extends GenericDAOImpl<Transaction, TransactionRepository> implements TransactionDAO {
    @Autowired
    public  TransactionDAOImpl (TransactionRepository repo) {super(repo);
    }


    @Override
    @Transactional
    public Transaction addTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setCryptoType(transactionDTO.getCryptoType());
        transaction.setValuePesos(transactionDTO.getValuePesos());
        transaction.setNominalValue(transactionDTO.getNominalValue());
        transaction.setValueCotization(transactionDTO.getValueCotization());
        transaction.setOperationUserNumber(transactionDTO.getOperationUserNumber());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setIsActive(transactionDTO.getIsActive());
        return transaction;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Transaction> transactionByCryptoName(CryptoEnum crypto) {
        Iterable<Transaction> transactions = repo.transactionByCryptoName(crypto);
        return transactions;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Transaction> transactionsActive() {
        Iterable<Transaction> transactions = repo.transactionsActive();
        return transactions;
    }
    @Override
    @Transactional
    public Transaction newSellIntention(User user, TransactionDTO transactionDTO){
        Transaction transaction = addTransaction(transactionDTO);
        transaction.setOtherUserId(user.getId());
        return transaction;
    }
}
