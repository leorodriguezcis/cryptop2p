package services;

import com.spring.universidad.cryptop2p.modelo.entidades.numeradores.CryptoEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class BinanceService {

    private final RestTemplate restTemplate;

    public BinanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getPrice(CryptoEnum symbol) {
        String url = "https://api1.binance.com/api/v3/ticker/price?symbol=" + symbol;
        TickerResponse response = restTemplate.getForObject(url, TickerResponse.class);
        return new BigDecimal(response.getPrice());
    }

}

class TickerResponse {
    private String symbol;
    private String price;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}