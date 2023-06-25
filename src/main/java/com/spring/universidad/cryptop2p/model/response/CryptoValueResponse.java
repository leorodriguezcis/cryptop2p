package com.spring.universidad.cryptop2p.model.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.spring.universidad.cryptop2p.model.entities.Crypto;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CryptoValueResponse implements Serializable {
    private CryptoEnum name;
    private double valueDollar;
    private double valuePesos;

    public CryptoValueResponse(Crypto crypto) {
        this.name = crypto.getName();
        this.valueDollar = crypto.getValue();
        this.valuePesos = crypto.getValueInArs();
    }

    public CryptoEnum getName() {
        return name;
    }

    public double getValueDollar() {
        return valueDollar;
    }

    public double getValuePesos() {
        return valuePesos;
    }
    
}
