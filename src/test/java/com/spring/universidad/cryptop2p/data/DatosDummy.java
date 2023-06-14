package com.spring.universidad.cryptop2p.data;

import com.spring.universidad.cryptop2p.modelo.entities.Crypto;
import com.spring.universidad.cryptop2p.modelo.entities.Transaction;
import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.numeradores.CryptoEnum;
import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.BigDecimalConversion;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DatosDummy {
    //users
    public static User user1 = new User("pepe","gomez","elpepe@gmail.com","san jose 1122", "1234","11119922AAA","11119922AAA");
    public static User user2 = new User("damian","pug","damipug@gmail.com","beriso 1122", "1234","11119922CCC","11119923BBB");
    public static User user3 = new User("laura","estevez","laueste@gmail.com","ascencio 1122", "1234","11119922BBB","11119922CCC");

    //cryptos
    public static Crypto ALICEUSDT = new Crypto(CryptoEnum.ALICEUSDT, LocalDate.now(),1.09300000,529.012);
    public static Crypto MATICUSDT = new Crypto(CryptoEnum.MATICUSDT, LocalDate.now(),0.76180000,365.664);
    public static Crypto AXSUSDT = new Crypto(CryptoEnum.AXSUSDT, LocalDate.now(),6.23000000,2990.4);
    public static Crypto AAVEUSDT = new Crypto(CryptoEnum.AAVEUSDT, LocalDate.now(),59.00000000,28320.0);
    public static Crypto ATOMUSDT = new Crypto(CryptoEnum.ATOMUSDT, LocalDate.now(),9.39800000,4511.04);
    public static Crypto NEOUSDT = new Crypto(CryptoEnum.NEOUSDT, LocalDate.now(),8.89000000,4267.2);
    public static Crypto DOTUSDT = new Crypto(CryptoEnum.DOTUSDT, LocalDate.now(),5.00800000,2403.84);
    public static Crypto ETHUSDT = new Crypto(CryptoEnum.ETHUSDT, LocalDate.now(),11831.25000000,879000.00);
    public static Crypto CAKEUSDT = new Crypto(CryptoEnum.CAKEUSDT, LocalDate.now(),1.53300000,735.84);
    public static Crypto BTCUSDT = new Crypto(CryptoEnum.BTCUSDT, LocalDate.now(),26308.99000000,12628315.2);
    public static Crypto BNBUSDT = new Crypto(CryptoEnum.BNBUSDT, LocalDate.now(),258.50000000,124080.00);
    public static Crypto ADAUSDT = new Crypto(CryptoEnum.ADAUSDT, LocalDate.now(),0.32180000,154.464);
    public static Crypto TRXUSDT = new Crypto(CryptoEnum.TRXUSDT, LocalDate.now(),0.07658000,36.7584);
    public static Crypto AUDIOUSDT = new Crypto(CryptoEnum.AUDIOUSDT, LocalDate.now(),0.20030000,96.144);

    //transactions
    public static Transaction transaction1 = new Transaction("sell",DOTUSDT,10d,DOTUSDT.getValueInArs()*10, new BigDecimal(DOTUSDT.getValue()));
    public static Transaction transaction2 = new Transaction("buy",ETHUSDT,5d,ETHUSDT.getValueInArs()*5, new BigDecimal(ETHUSDT.getValue()));

    public static Transaction transaction3 = new Transaction("buy",AAVEUSDT,102d,AAVEUSDT.getValueInArs()*5, new BigDecimal(AAVEUSDT.getValue()));


    public static Transaction getTransaction1() {
        return transaction1;
    }

    public static Transaction getTransaction2() { return transaction2; }

    public static Transaction getTransaction3() {
        transaction3.setUser(user3);
        return transaction3;
    }

    public static User getUser1() {
        return user1;
    }

    public static User getUser2() {
        return user2;
    }

    public static User getUser3() {
        return user3;
    }
}
