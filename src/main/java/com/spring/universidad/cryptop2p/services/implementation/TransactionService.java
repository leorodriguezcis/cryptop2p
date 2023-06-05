package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.DateRangeDTO;
import com.spring.universidad.cryptop2p.modelo.entities.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.TransactionState;
import com.spring.universidad.cryptop2p.modelo.entities.repository.CryptoRepository;
import com.spring.universidad.cryptop2p.modelo.entities.repository.TransactionRepository;
import com.spring.universidad.cryptop2p.modelo.entities.repository.UserRepository;
import com.spring.universidad.cryptop2p.services.interfaces.TransactionDAO;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService extends GenericService<Transaction, TransactionRepository> implements TransactionDAO {
    private static final  String MSG_SUCCESS = "SUCCESS";
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
    public Map<String, Object> transactionByCryptoName(CryptoEnum crypto) {
        Map<String, Object> message = new HashMap<>();
        Iterable<Transaction> transactions = repo.transactionByCryptoName(crypto);
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put("datos", transactions);
        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> transactionsActive() {
        Map<String, Object> message = new HashMap<>();
        Iterable<Transaction> transactions = repo.transactionsActive();
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put("datos", transactions);
        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Transaction> searchByRangeActivity(DateRangeDTO dateRange, Integer userId) {
        LocalDateTime start = convertToLocalDateTime(dateRange.getStartDate());
        LocalDateTime end = convertToLocalDateTime(dateRange.getEndDate());
        return repo.searchByRangeActivity(start, end, userId);
    }
    @Transactional
    @Override
    public String buyAnIntention(Integer userId, Integer transactionID) {
        Transaction transaction = repo.findById(transactionID).get();
        transaction.setOtherUserId(userId);
        transaction.setIsActive(TransactionState.ON_PROCESS);
        repo.save(transaction);
        return  userRepository.findById(userId).get().getWallet();
    }
    @Transactional
    @Override
    public String sellAnIntention(Integer userId, Integer transactionID) {
        Transaction transaction = repo.findById(transactionID).get();
        transaction.setOtherUserId(userId);
        transaction.setIsActive(TransactionState.ON_PROCESS);
        repo.save(transaction);
        return  userRepository.findById(userId).get().getCvu();
    }
    @Transactional
    @Override
    public Map<String, Object> confirmTransference(Integer userId, Integer transactionID, String action) {
        Map<String, Object> message = new HashMap<>();
        Optional<User> userRes = userRepository.findById(userId);
        Optional<Transaction> transaction = repo.findById(transactionID);
        if (transaction.isEmpty()) {
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put("message", String.format("no existe ninguna transaccion con id: %s", transactionID));
            return message;
        }
        if (userRes.isEmpty()) {
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put("message", String.format("no existe ningun usuario con id: %s", userId));
            return message;
        }
        User user = userRepository.findById(userId).get();
        if(user.getId()==transaction.get().getOtherUserId()&&action=="send"){
            transaction.get().setIsActive(TransactionState.CONFIRMEDSEND);
        }
        if(user.getId()==transaction.get().getOtherUserId()&&action=="receive"){
            transaction.get().setIsActive(TransactionState.CONFIRMEDRECEIVE);
        }
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put("datos", transaction.get());
        repo.save(transaction.get());
        return(message);
    }
    @Transactional
    @Override
    public Map<String, Object> cancelTransaction(Integer userId, Integer transactionID) {
        Map<String, Object> message = new HashMap<>();
        Optional<User> user = userRepository.findById(userId);
        Optional<Transaction> transaction = repo.findById(transactionID);
        if (transaction.isEmpty()) {
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put("message", String.format("no existe ninguna transaccion con id: %s", transactionID));
            return message;
        }
        if (user.isEmpty()) {
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put("message", String.format("no existe ningun usuario con id: %s", userId));
            return message;
        }
        User userRes = userRepository.findById(userId).get();
        Transaction transRes = repo.findById(transactionID).get();
        if(userId.equals(transRes.getOtherUserId()) || userId.equals(transRes.getUser().getId())){
            transRes.setIsActive(TransactionState.CANCELLED);
            repo.save(transRes);
            userRes.cancelTransaction();
            userRepository.save(userRes);
            repo.save(transRes);
            message.put(MSG_SUCCESS, Boolean.TRUE);
            message.put("Se elimino la transaccion con id:", transactionID);
            return message;
        }
        else{
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put("El usuario no pertenece a esta transaccion", userId);
            return message;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> BuscarTransaccion(Integer transactionId) {
        Map<String, Object> message = new HashMap<>();
        Optional<Transaction> transactionO = repo.findById(transactionId);
        if(transactionO.isEmpty()){
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put("message", String.format("no existe ninguna transaccion con id: %s", transactionId));
            return message;
        }
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put("datos", transactionO.get());
        return message;
    }



    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return LocalDateTime.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }


}
