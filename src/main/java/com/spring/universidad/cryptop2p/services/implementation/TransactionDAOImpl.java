package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.modelo.entities.repository.TransactionRepository;
import com.spring.universidad.cryptop2p.services.interfaces.TransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDAOImpl extends GenericDAOImpl<Transaction, TransactionRepository> implements TransactionDAO {

    @Autowired
    public  TransactionDAOImpl (TransactionRepository repo) {super(repo);
    }

    @Override
    public Transaction addTransaction(TransactionDTO transactionDTO) {
        return null;
    }
}
