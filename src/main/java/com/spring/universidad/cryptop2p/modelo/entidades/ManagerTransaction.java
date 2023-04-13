package com.spring.universidad.cryptop2p.modelo.entidades;

import com.spring.universidad.cryptop2p.modelo.entidades.numeradores.CryptoEnum;

import java.util.ArrayList;

public class ManagerTransaction {
    private ArrayList<Transaction> listTransaction = new ArrayList<>();
    private Crypto crypto;
    public ManagerTransaction(){
    }

    public void addTransaction(Transaction transaction){
        this.listTransaction.add(transaction);
    }
}
