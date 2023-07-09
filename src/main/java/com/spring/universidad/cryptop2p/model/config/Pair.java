package com.spring.universidad.cryptop2p.model.config;

public class  Pair {
    private final Boolean booleanValue;
    private final Integer integerValue;

    public Pair(Boolean booleanValue, Integer integerValue) {
        this.booleanValue = booleanValue;
        this.integerValue = integerValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

}

