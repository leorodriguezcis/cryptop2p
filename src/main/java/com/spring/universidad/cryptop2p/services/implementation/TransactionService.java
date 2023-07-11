package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.model.dto.CryptoDTO;
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
    private static final  String TRANSACTION_MSG = "doesn't find transaction with id: %s";
    private UserRepository userRepository;
    private CryptoRepository cryptoRepository;

    @Autowired
    public TransactionService(TransactionRepository repo, UserRepository userRepo, CryptoRepository cryptoRepo) {
        super(repo);
        this.userRepository = userRepo;
        this.cryptoRepository = cryptoRepo;
    }


    @Transactional
    public Map<String, Object> addTransaction(TransactionDTO transactionDTO, int userId, String email) {
        Map<String, Object> message = new HashMap<>();
        Transaction transaction = new Transaction();
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Crypto> cryptoOpt = cryptoRepository.findCryptosByName(transactionDTO.getCryptoType());
        if (!cryptoOpt.isPresent() || !userOpt.isPresent()) {
            return cannotFind();
        }
        User user;
        Crypto crypto;
        user = userOpt.get();
        if(validateEmailWithId(user, email)||!transactionDTO.getUser().equals(email)){return userNotValid();}
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
        message.put("Was adjust:",res.doubleValue() != transactionDTO.getValueCotization().doubleValue());
        message.put(DATOS, transactionDTO.getTransactionType().equals(TransactionType.SELL) ? user.getCvu(): user.getWallet());
        return message;
    }

    private boolean validateEmailWithId(User user, String email) {
        return !user.getEmail().equals(email);
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
            return cannotFind();
        }
        if(validateEmailWithId(userRes.get(), email)){return userNotValid();}
        transaction = res.get();
        if(transaction.getUser().getEmail().equals(email)){
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, "you cannot buy or sell your own transaction");
            return message;
        }
        if(transaction.getTransactionType().equals(intention)){
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, "Invalid Action");
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
        if(!userRes.isPresent() || !transaction.isPresent()){return cannotFind();}
        User user = userRes.get();
        if(validateEmailWithId(user, email)){return userNotValid();}
        Transaction transRes = transaction.get();
        if (transRes.transactionState.equals(TransactionState.CANCELLED)){
            repo.save(transRes);
            return cancelTransMSG();
        }
        if(action.equals("send")&&user.getId().equals(transRes.getOtherUserId())){
           if(!user.getEmail().equals(email)){
            return invalidAction();
           }
           transRes.setIsActive(TransactionState.CONFIRMED);
        }
        if(action.equals("receive")&&user.getId().equals(transRes.getUser().getId())){
            if(!user.getEmail().equals(email)){
                return invalidAction();
           }
            if(transRes.getIsActive()==TransactionState.CONFIRMED){
                return setFinishedTransaction(transRes, message);
            }
            else{
                message.put(MSG_SUCCESS, Boolean.FALSE);
                message.put(MESSAGE, String.format("The transaction cannot be finalized if the user did not confirm the operation, the user needs to confirm: %s", userId));
                return message;
            }
        }
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put(DATOS,transRes);
        repo.save(transRes);
        return(message);
    }
    private  Map<String, Object> setFinishedTransaction(Transaction transRes, Map<String, Object> message) {
        transRes.setIsActive(TransactionState.FINISHED);
        Optional<User> user1o = userRepository.findById(transRes.getOtherUserId());
        Optional<User> user2o = userRepository.findById(transRes.getUser().getId());
        if (!user1o.isPresent() || !user2o.isPresent()) {return cannotFind();}
        userRepository.save(user1o.get().finishedTransaction(transRes.getTransactionDate(), LocalDateTime.now()));
        userRepository.save(user2o.get().finishedTransaction(transRes.getTransactionDate(), LocalDateTime.now()));
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
        if (!user.isPresent() || !transaction.isPresent()) {return cannotFind();}
        User userRes =  user.get();
        Transaction transRes = transaction.get();
        if(!transRes.getUser().getEmail().equals(email)){
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put(MESSAGE, "Only the user who owns the transaction can cancel it");
            return message;
        }
        if(userId.equals(transRes.getOtherUserId()) || userId.equals(transRes.getUser().getId())){
            transRes.setIsActive(TransactionState.CANCELLED);
            repo.save(transRes);
            if(transRes.transactionState.equals(TransactionState.CONFIRMED) || 
               transRes.transactionState.equals(TransactionState.ON_PROCESS)){
                userRes.cancelTransaction();
                userRepository.save(userRes);}
            repo.save(transRes);
            message.put(MSG_SUCCESS, Boolean.TRUE);
            message.put("The transaction with id was deleted:", transactionID);
            return message;
        }
        else{
            message.put(MSG_SUCCESS, Boolean.FALSE);
            message.put("The user does not belong to this transaction", userId);
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

    public Transaction checkCotizationDifferece(Transaction transaction){
        CryptoService cryptoService = new CryptoService(this.cryptoRepository);
        CryptoDTO cryptoDTO = cryptoService.getCotizationBySymbol(transaction.getCrypto().getName());
        if((transaction.getTransactionType().equals(TransactionType.BUY) && ((transaction.valueCotization.doubleValue()) >= cryptoDTO.getPrice()))||
            (transaction.getTransactionType().equals(TransactionType.SELL) && ((transaction.valueCotization.doubleValue()) <= cryptoDTO.getPrice()))){
            transaction.setIsActive(TransactionState.CANCELLED);
        }
        return transaction;
    }
    public Map<String, Object> invalidAction(){
        Map<String, Object> message = new HashMap<>();
        message.put(MSG_SUCCESS, Boolean.FALSE);
        message.put(MESSAGE, "Invalid action for this user");
        return message;
    }
    public Map<String, Object> cannotFind(){
        Map<String, Object> message = new HashMap<>();
        message.put(MSG_SUCCESS, Boolean.FALSE);
        message.put(MESSAGE, "cant find transaction/user or crypto");
        return message;
    }
    public Map<String, Object> userNotValid(){
        Map<String, Object> message = new HashMap<>();
        message.put(MSG_SUCCESS, Boolean.FALSE);
        message.put(MESSAGE, "the user or the id are invalid or do not match");
        return message;
    }
    public Map<String, Object> cancelTransMSG(){
        Map<String, Object> message = new HashMap<>();
        message.put(MSG_SUCCESS, Boolean.FALSE);
        message.put(MESSAGE, "The transaction was canceled due to the difference in the quoted price");
        return message;
    }
}
