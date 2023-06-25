package com.spring.universidad.cryptop2p.model.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.spring.universidad.cryptop2p.model.entities.Transaction;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CryptoActiveResult implements Serializable {
    LocalDateTime date;
    double volumeDollar;
    double volumeARS;
    private List<CryptoActive> cryptoActiveList;
    public CryptoActiveResult(List<Transaction> transactions ){
        this.date = LocalDateTime.now();
        this.volumeARS = this.getTotalValueArs(transactions);
        this.volumeDollar = this.getTotalDollar(transactions);
        this.cryptoActiveList =  getCryptoActiveList(transactions);
    }
    public List<CryptoActive> getCryptoActiveList(List<Transaction> transactions){
        List<CryptoActive> resList = new ArrayList<>();
        for (Transaction transaction : transactions){
            resList.add(new CryptoActive(transaction));
        }
        return resList;
    }
    public double getTotalDollar(List<Transaction> transactions ){
        double counterDollar = 0;
        for (Transaction transaction : transactions){
            counterDollar += transaction.getNominalValue()* Double.valueOf(transaction.getValueCotization().toString());
        }
        return counterDollar;
    }
    public double getTotalValueArs(List<Transaction> transactions ){
        double counterARS = 0;
        for (Transaction transaction : transactions){
            counterARS += transaction.getValuePesos();
        }
        return counterARS;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public double getVolumeDollar() {
        return volumeDollar;
    }
    public double getVolumeARS() {
        return volumeARS;
    }
    public List<CryptoActive> getCryptoActiveList() {
        return cryptoActiveList;
    }
    

}
