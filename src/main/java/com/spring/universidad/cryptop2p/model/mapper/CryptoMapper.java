package com.spring.universidad.cryptop2p.model.mapper;

import java.util.EnumMap;
import java.util.Map;

import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;

public class CryptoMapper {

    Map<CryptoEnum, String> enumMap = new EnumMap<>(CryptoEnum.class);
    public CryptoMapper (){
        enumMap.put(CryptoEnum.ALICEUSDT,"ALICE");
        enumMap.put(CryptoEnum.MATICUSDT,"WMATIC");
        enumMap.put(CryptoEnum.AXSUSDT,"AXS");
        enumMap.put(CryptoEnum.AAVEUSDT,"AAVE");
        enumMap.put(CryptoEnum.ATOMUSDT,"ATOM");
        enumMap.put(CryptoEnum.NEOUSDT,"NEO");
        enumMap.put(CryptoEnum.DOTUSDT,"DOT");
        enumMap.put(CryptoEnum.ETHUSDT,"ETH");
        enumMap.put(CryptoEnum.BTCUSDT,"BTC");
        enumMap.put(CryptoEnum.BNBUSDT,"BNB");
        enumMap.put(CryptoEnum.ADAUSDT,"ADA");
        enumMap.put(CryptoEnum.TRXUSDT,"TRX");
        enumMap.put(CryptoEnum.AUDIOUSDT,"AUDIO");
    }

    public String getElements(CryptoEnum cryptoEnum){
        return enumMap.get(cryptoEnum);
    }
}
