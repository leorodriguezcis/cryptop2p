package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.model.response.CryptoActiveResult;
import com.spring.universidad.cryptop2p.model.entities.Crypto;
import com.spring.universidad.cryptop2p.model.entities.Transaction;
import com.spring.universidad.cryptop2p.model.entities.User;
import com.spring.universidad.cryptop2p.model.dto.DateRangeDTO;
import com.spring.universidad.cryptop2p.model.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import com.spring.universidad.cryptop2p.model.enums.TransactionState;
import com.spring.universidad.cryptop2p.model.repository.CryptoRepository;
import com.spring.universidad.cryptop2p.model.repository.TransactionRepository;
import com.spring.universidad.cryptop2p.model.repository.UserRepository;
import com.spring.universidad.cryptop2p.services.interfaces.TransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class TransactionService extends GenericService<Transaction, TransactionRepository> implements TransactionDAO {
    private static final  String MSG_SUCCESS = "SUCCESS";
    private static final  String DATOS = "datos";
    private static final  String MESSAGE = "message";
     private static final  String TRANSACTION_MSG = "no existe ninguna transaccion con id: %s";
    
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
        Optional<User> userOpt = userRepository.findById(user_id);
        User user = new User();
        if(userOpt.isPresent()){
            user = userOpt.get();
        }
        Optional<Crypto> cryptoOpt = cryptoRepository.findCryptosByName(transactionDTO.getCryptoType());
        Crypto crypto = new Crypto();
        if(cryptoOpt.isPresent()){
            crypto = cryptoOpt.get();
        }
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setValuePesos(transactionDTO.getValuePesos());
        transaction.setNominalValue(transactionDTO.getNominalValue());
        transaction.setValueCotization(transactionDTO.getValueCotization());
        transaction.setOperationUserNumber(transactionDTO.getOperationUserNumber());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setIsActive(transactionDTO.getState());
        transaction.setCrypto(crypto);
        repo.save(transaction);
        return transactionDTO.getTransactionType().equals("sell") ? user.getCvu(): user.getWallet();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> transactionByCryptoName(CryptoEnum crypto) {
        Map<String, Object> message = new HashMap<>();
        Iterable<Transaction> transactions = repo.transactionByCryptoName(crypto);
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put(DATOS, transactions);
        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> transactionsActive() {
        Map<String, Object> message = new HashMap<>();
        Iterable<Transaction> transactions = repo.transactionsActive();
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put(DATOS, transactions);
        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public CryptoActiveResult searchByRangeActivity(DateRangeDTO dateRange, Integer userId) {
        LocalDateTime start = convertToLocalDateTime(dateRange.getStartDate());
        LocalDateTime end = convertToLocalDateTime(dateRange.getEndDate());
        ArrayList<Transaction> transactionRes = (ArrayList<Transaction>)(repo.searchByRangeActivity(start, end, userId));
        return new CryptoActiveResult(transactionRes);
    }
    @Transactional
    @Override
    public String buyAnIntention(Integer userId, Integer transactionID) {
        Optional<Transaction> res = repo.findById(transactionID);
        Transaction transaction = new Transaction();
        if (res.isPresent()){ 
             transaction = res.get();
        }
        transaction.setIsActive(TransactionState.ON_PROCESS);
        repo.save(transaction);
        Optional<User> userRes = userRepository.findById(userId);
        String resWallet = "";
        if (userRes.isPresent()){
            resWallet = userRes.get().getWallet();
        }
        return  resWallet;
    }



    @Transactional
    @Override
    public String sellAnIntention(Integer userId, Integer transactionID) {
        Optional<Transaction> res = repo.findById(transactionID);
        Transaction transaction = new Transaction();
        if(res.isPresent()){
         transaction = res.get();}
        transaction.setOtherUserId(userId);
        transaction.setIsActive(TransactionState.ON_PROCESS);
        repo.save(transaction);
        Optional<User> userRes = userRepository.findById(userId);
        String cvu = "";
        if (userRes.isPresent()){
            cvu = userRes.get().getCvu();
        }
        return  cvu;
    }
    @Transactional
    @Override
    public Map<String, Object> confirmTransference(Integer userId, Integer transactionID, String action) {
        Map<String, Object> message = new HashMap<>();
        Optional<User> userRes = userRepository.findById(userId);
        Optional<Transaction> transaction = repo.findById(transactionID);
        if (transaction.isEmpty()) {
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, String.format(TRANSACTION_MSG, transactionID));
            return message;
        }
        if (userRes.isEmpty()) {
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, String.format("no existe ningun usuario con id: %s", userId));
            return message;
        }
        User user = userRes.get();
        if(user.getId().equals(transaction.get().getOtherUserId())&&action.equals("send")){
            transaction.get().setIsActive(TransactionState.CONFIRMED);
        }
        if(user.getId().equals(transaction.get().getOtherUserId())&&action.equals("receive")){
            if(transaction.get().getIsActive()==TransactionState.CONFIRMED){transaction.get().setIsActive(TransactionState.FINISHED);}
            else{
                message.put(MSG_SUCCESS, Boolean.FALSE);
                message.put(MESSAGE, String.format("no se puede finalizar la transaccion si el usuario no confirmo la operacion, usuario falta confirmar: %s", userId));
                return message;
            }
        }
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put(DATOS, transaction.get());
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
            message.put(MESSAGE, String.format(TRANSACTION_MSG, transactionID));
            return message;
        }
        if (user.isEmpty()) {
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, String.format("no existe ningun usuario con id: %s", userId));
            return message;
        }
        User userRes =  user.get();
        Transaction transRes = transaction.get();
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
    public Map<String, Object> findTransaction(Integer transactionId) {
        Map<String, Object> message = new HashMap<>();
        Optional<Transaction> transactionO = repo.findById(transactionId);
        if(transactionO.isEmpty()){
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, String.format(TRANSACTION_MSG, transactionId));
            return message;
        }
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put(DATOS, transactionO.get());
        return message;
    }

    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return LocalDateTime.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }


}
