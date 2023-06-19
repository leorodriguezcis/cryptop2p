package com.spring.universidad.cryptop2p.model.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.spring.universidad.cryptop2p.model.entities.Transaction;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CryptoActiveResult implements Serializable {
    LocalDateTime date;
    double volumeDollar;
    double volumeARS;
    ArrayList<CryptoActive> cryptoActiveList;
    public CryptoActiveResult(ArrayList<Transaction> transactions ){
        this.date = LocalDateTime.now();
        this.volumeARS = this.getTotalValueArs(transactions);
        this.volumeDollar = this.getTotalDollar(transactions);
        this.cryptoActiveList =  getCryptoActiveList(transactions);
    }
    public ArrayList<CryptoActive> getCryptoActiveList(ArrayList<Transaction> transactions){
        ArrayList<CryptoActive> resList = new ArrayList<>();
        for (Transaction transaction : transactions){
            resList.add(new CryptoActive(transaction));
        }
        return resList;
    }
    public double getTotalDollar(ArrayList<Transaction> transactions ){
        double counterDollar = 0;
        for (Transaction transaction : transactions){
            counterDollar += transaction.getNominalValue()* Double.valueOf(transaction.getValueCotization().toString());
        }
        return counterDollar;
    }
    public double getTotalValueArs(ArrayList<Transaction> transactions ){
        double counterARS = 0;
        for (Transaction transaction : transactions){
            counterARS += transaction.getValuePesos();
        }
        return counterARS;
    }

}
