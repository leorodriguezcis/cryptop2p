package com.spring.universidad.cryptop2p.model.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.spring.universidad.cryptop2p.model.entities.Transaction;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;

import java.io.Serializable;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CryptoActive implements Serializable {
    CryptoEnum crypto;
    double nominalValue;
    double cotizationCrypto;
    double cotizationCrytoARS;
    public CryptoActive(Transaction transactions) {
        this.crypto = transactions.getCrypto().name;
        this.nominalValue = transactions.getNominalValue();
        this.cotizationCrypto = transactions.getCrypto().getValue();
        this.cotizationCrytoARS = transactions.getCrypto().getValueInArs();
    }
}
