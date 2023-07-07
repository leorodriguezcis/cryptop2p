package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.model.response.CryptoActiveResult;
import com.spring.universidad.cryptop2p.model.entities.Crypto;
import com.spring.universidad.cryptop2p.model.entities.Transaction;
import com.spring.universidad.cryptop2p.model.entities.User;
import com.spring.universidad.cryptop2p.model.dto.DateRangeDTO;
import com.spring.universidad.cryptop2p.model.dto.TransactionDTO;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import com.spring.universidad.cryptop2p.model.enums.TransactionState;
import com.spring.universidad.cryptop2p.model.enums.TransactionType;
import com.spring.universidad.cryptop2p.model.repository.CryptoRepository;
import com.spring.universidad.cryptop2p.model.repository.TransactionRepository;
import com.spring.universidad.cryptop2p.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class TransactionService extends GenericService<Transaction, TransactionRepository> {
    private static final  String MSG_SUCCESS = "SUCCESS";
    private static final  String DATOS = "datos";
    private static final  String MESSAGE = "message";
    private static final  String TRANSACTION_MSG = "no existe ninguna transaccion con id: %s";
    private static final  String USER_MSG = "no existe ningun usuario con id: %s";
    private UserRepository userRepository;
    private CryptoRepository cryptoRepository;

    @Autowired
    public TransactionService(TransactionRepository repo, UserRepository userRepo, CryptoRepository cryptoRepo) {
        super(repo);
        this.userRepository = userRepo;
        this.cryptoRepository = cryptoRepo;
    }


    @Transactional
    public Map<String, Object> addTransaction(TransactionDTO transactionDTO, int userId) {
        Map<String, Object> message = new HashMap<>();
        Transaction transaction = new Transaction();
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Crypto> cryptoOpt = cryptoRepository.findCryptosByName(transactionDTO.getCryptoType());
        if (userOpt.isEmpty()) {
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, String.format(USER_MSG, userId));
            return message;
        }
        if (cryptoOpt.isEmpty()) {
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, String.format("no existe ninguna crypto con el nombre: %s", transactionDTO.getCryptoType()));
            return message;
        }
        User user;
        Crypto crypto;
        user = userOpt.get();
        crypto = cryptoOpt.get();
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setValuePesos(transactionDTO.getValuePesos());
        transaction.setNominalValue(transactionDTO.getNominalValue());
        BigDecimal res = transaction.validateCotization(transactionDTO.getValueCotization(),crypto);
        transaction.setValueCotization(res);
        transaction.setOperationUserNumber(transactionDTO.getOperationUserNumber());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setIsActive(TransactionState.NEW);
        transaction.setCrypto(crypto);
        transaction.setUser(user);
        repo.save(transaction);
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put(DATOS, transactionDTO.getTransactionType().equals(TransactionType.SELL) ? user.getCvu(): user.getWallet());
        return message;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> transactionByCryptoName(CryptoEnum crypto) {
        Map<String, Object> message = new HashMap<>();
        Iterable<Transaction> transactions = repo.transactionByCryptoName(crypto);
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put(DATOS, transactions);
        return message;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> transactionsActive() {
        Map<String, Object> message = new HashMap<>();
        Iterable<Transaction> transactions = repo.transactionsActive();
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put(DATOS, transactions);
        return message;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> searchByRangeActivity(DateRangeDTO dateRange, Integer userId) {
        Map<String, Object> message = new HashMap<>();
        LocalDateTime start = convertToLocalDateTime(dateRange.getStartDate());
        LocalDateTime end = convertToLocalDateTime(dateRange.getEndDate());
        ArrayList<Transaction> transactionRes = (ArrayList<Transaction>)(repo.searchByRangeActivity(start, end, userId));
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put(DATOS, new CryptoActiveResult(transactionRes));
        return message;
    }
    @Transactional
    public Map<String, Object> sellOrBuyAnIntention(Integer userId, Integer transactionID, TransactionType intention, String email) {
        Map<String, Object> message = new HashMap<>();
        Optional<Transaction> res = repo.findById(transactionID);
        Optional<User> userRes = userRepository.findById(userId);
        Transaction transaction;
        String userWallet = "";
        if (!res.isPresent() || !userRes.isPresent()){
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, String.format("no existe usuario con id: %s o transaccion con id: %s2", userId, transactionID));
            return message;
        }
        transaction = res.get();
        if(transaction.getUser().getEmail().equals(email)){
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, "no puede comprar o vender su propia transaccion");
            return message;
        }
        if(transaction.getTransactionType().equals(intention)){
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, "Accion invalida");
            return message;
        }
        userWallet = userRes.get().getWallet();
        String userCVU = userRes.get().getCvu();
        transaction.setIsActive(TransactionState.ON_PROCESS);
        transaction.setOtherUserId(userId);
        repo.save(transaction);
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put(DATOS, intention.equals(TransactionType.BUY) ? userWallet : userCVU);
        return  message;
    }

    @Transactional
    public Map<String, Object> confirmTransference(Integer userId, Integer transactionID, String action, String email) {
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
            message.put(MESSAGE, String.format(USER_MSG, userId));
            return message;
        }
        User user = userRes.get();
        Transaction transRes = transaction.get();
        if(action.equals("send")&&user.getId().equals(transRes.getOtherUserId())){
           if(!user.getEmail().equals(email)){
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, "Accion invalida para el usuario");
            return message;
           }
           transRes.setIsActive(TransactionState.CONFIRMED);
        }
        if(action.equals("receive")&&user.getId().equals(transRes.getUser().getId())){
            if(!user.getEmail().equals(email)){
                message.put(MSG_SUCCESS, Boolean.FALSE);
                message.put(MESSAGE, "Accion invalida para el usuario");
                return message;
           }
            if(transRes.getIsActive()==TransactionState.CONFIRMED){
                transRes.setIsActive(TransactionState.FINISHED);
                Optional<User> user1o = userRepository.findById(transRes.getOtherUserId());
                Optional<User> user2o = userRepository.findById(transRes.getUser().getId());
                if (user1o.isEmpty() || user2o.isEmpty()) {
                 message.put(MSG_SUCCESS, Boolean.FALSE);
                 message.put(MESSAGE, (USER_MSG));
                 return message;
                }
                User user1 = user1o.get();
                User user2 =user2o.get();
                user1.finishedTransaction(transRes.getTransactionDate(), LocalDateTime.now());
                user2.finishedTransaction(transRes.getTransactionDate(), LocalDateTime.now());
                userRepository.save(user1);
                userRepository.save(user2);
            }
            else{
                message.put(MSG_SUCCESS, Boolean.FALSE);
                message.put(MESSAGE, String.format("no se puede finalizar la transaccion si el usuario no confirmo la operacion, usuario falta confirmar: %s", userId));
                return message;
            }
        }
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put(DATOS,transRes);
        repo.save(transRes);
        return(message);
    }
    @Transactional
    public Map<String, Object> cancelTransaction(Integer userId, Integer transactionID, String email) {
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
            message.put(MESSAGE, String.format(USER_MSG, userId));
            return message;
        }
        User userRes =  user.get();
        Transaction transRes = transaction.get();
        if(!transRes.getUser().getEmail().equals(email)){
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, "Solo el usuario dueño de la transaccion puede cancelarla");
            return message;
        }
        if(userId.equals(transRes.getOtherUserId()) || userId.equals(transRes.getUser().getId())){
            transRes.setIsActive(TransactionState.CANCELLED);
            repo.save(transRes);
            if(transRes.transactionState.equals(TransactionState.CONFIRMED)){
                userRes.cancelTransaction();
                userRepository.save(userRes);}
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
