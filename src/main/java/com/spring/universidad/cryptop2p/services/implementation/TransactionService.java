package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.modelo.entities.repository.CryptoRepository;
import com.spring.universidad.cryptop2p.modelo.entities.repository.TransactionRepository;
import com.spring.universidad.cryptop2p.modelo.entities.repository.UserRepository;
import com.spring.universidad.cryptop2p.services.interfaces.TransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService extends GenericService<Transaction, TransactionRepository> implements TransactionDAO {

    private UserRepository userRepository;
    private CryptoRepository cryptoRepository;
    @Autowired
    public TransactionService(TransactionRepository repo, UserRepository userRepo, CryptoRepository cryptoRepo) {
        super(repo);
        this.userRepository = userRepo;
        this.cryptoRepository = cryptoRepo;
    }


    @Override
    @Transactional
    public String addTransaction(TransactionDTO transactionDTO, int user_id) {
        Transaction transaction = new Transaction();
        User user =userRepository.findById(user_id).get();
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setCryptoType(transactionDTO.getCryptoType());
        transaction.setValuePesos(transactionDTO.getValuePesos());
        transaction.setNominalValue(transactionDTO.getNominalValue());
        transaction.setValueCotization(transactionDTO.getValueCotization());
        transaction.setOperationUserNumber(transactionDTO.getOperationUserNumber());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setIsActive(transactionDTO.getState());
        transaction.setUser(user);
        transaction.setCrypto(cryptoRepository.findCryptosByName(transactionDTO.getCryptoType()).get());
        repo.save(transaction);
        return transactionDTO.getTransactionType() == "sell" ? user.getCvu(): user.getWallet();
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
        Transaction transaction = addTransaction(transactionDTO, user.getId());
        transaction.setOtherUserId(user.getId());
        return transaction;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Transaction> searchByRangeActivity(LocalDateTime start, LocalDateTime end) {
        return repo.searchByRangeActivity(start, end);
    }
}
