package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.model.entities.Crypto;
import com.spring.universidad.cryptop2p.model.dto.CryptoDTO;
import com.spring.universidad.cryptop2p.model.dto.DolarDTOHelper;
import com.spring.universidad.cryptop2p.model.enums.CryptoEnum;
import com.spring.universidad.cryptop2p.model.mapper.CryptoMapper;
import com.spring.universidad.cryptop2p.model.repository.CryptoRepository;
import com.spring.universidad.cryptop2p.model.response.CryptoValueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CryptoService extends GenericService<Crypto, CryptoRepository>{
    ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate= new RestTemplate();
    private static final  String MSG_SUCCESS = "SUCCESS";
    @Autowired
    public CryptoService(CryptoRepository repo) {super(repo);
    }

    @Transactional
    public Crypto createCrypto(CryptoDTO cryptoDTO){
        Crypto crypto = new Crypto();
        crypto.setDate(cryptoDTO.getHourCotization().toLocalDate());
        crypto.setName(cryptoDTO.getSymbolToEnum());
        crypto.setValue(cryptoDTO.getPrice());
        crypto.setValueInArs(cryptoDTO.getPriceArs());
        repo.save(crypto);
        return  crypto;
    }
    public CryptoDTO getCotizationBySymbol(CryptoEnum symbol) {
        NumberFormat nf = NumberFormat.getInstance();
        Double dollarPrice = null;
        try {
            dollarPrice = nf.parse(priceUsd()).doubleValue();
        } catch (ParseException e) {
            throw new BadRequestException("Error al convertir");
        }
        LocalDateTime hour = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
        String url = "https://api.binance.us/api/v3/ticker/price?symbol=" + symbol;
        CryptoDTO crypto = restTemplate.getForObject(url, CryptoDTO.class);
        Double priceCriptoInUsd = 0d;
        if(crypto != null){
             priceCriptoInUsd += crypto.getPrice();}
        else{
            throw new BadRequestException("Error al obtener valor");}
        Double priceArs = priceCriptoInUsd * dollarPrice;
        crypto.setPriceArs(priceArs);
        crypto.setHourCotization(hour);
        crypto.setSymbolToEnum(symbol);
        return crypto;
    }

    @Transactional
    public Boolean inicializerCrypto() {
        CryptoEnum[] crypotoList = new CryptoEnum[13];
        crypotoList[0] = CryptoEnum.ALICEUSDT;
        crypotoList[1] = CryptoEnum.MATICUSDT;
        crypotoList[2] = CryptoEnum.AXSUSDT ;
        crypotoList[3] = CryptoEnum.AAVEUSDT;
        crypotoList[4] = CryptoEnum.ATOMUSDT;
        crypotoList[5] = CryptoEnum.NEOUSDT;
        crypotoList[6] = CryptoEnum.DOTUSDT;
        crypotoList[7] = CryptoEnum.ETHUSDT;
        crypotoList[8] = CryptoEnum.BTCUSDT;
        crypotoList[9] = CryptoEnum.BNBUSDT;
        crypotoList[10] = CryptoEnum.ADAUSDT;
        crypotoList[11] = CryptoEnum.TRXUSDT ;
        crypotoList[12] = CryptoEnum.AUDIOUSDT;
        for(CryptoEnum cryptoEnum:crypotoList){
            createCrypto(getCotizationBySymbol(cryptoEnum));
        }

        return true;
    }

    @Transactional(readOnly = true)
    public Optional<Crypto> findCryptosByName(CryptoEnum nombre) {
        return repo.findCryptosByName(nombre);
    }
    @Transactional(readOnly = true)
    @Cacheable("getCryptos")
    public Map<String, Object> getAllCryptoCotization() {
        Map<String, Object> message = new HashMap<>();
        ArrayList<Crypto> arrayCrypto = (ArrayList<Crypto>)repo.findAll();
        ArrayList<CryptoValueResponse> arrayCryptoValueResponse = new ArrayList<>();
        for (Crypto crypto: arrayCrypto) {
            CryptoValueResponse cryptoResponse = new CryptoValueResponse(crypto);
            arrayCryptoValueResponse.add(cryptoResponse);
        }
        message.put(MSG_SUCCESS, Boolean.TRUE);
        message.put("datos", arrayCryptoValueResponse);
        return message;
    }

    public String priceUsd(){
        String url ="https://www.dolarsi.com/api/api.php?type=valoresprincipales";
		DolarDTOHelper[] casa = restTemplate.getForObject(url, DolarDTOHelper[].class);
        if(casa == null){
            throw new BadRequestException("Error al obtener valor");
        }
       return casa[1].getCasa().getCompra();
    }
    public class BadRequestException extends RuntimeException{

        public BadRequestException(String message) {
            super(message);
        }
    }
    @Cacheable("getCryptos")
    public Map<String, Object> getCotizationBySymbol24hs(CryptoEnum symbol) {
        CryptoMapper cryptoMapper = new CryptoMapper();
        Map<String, Object> message = new HashMap<>();
        String apiUrl = String.format("https://min-api.cryptocompare.com/data/v2/histohour?fsym=%s&tsym=USD&limit=24", cryptoMapper.getElements(symbol));
        String response = restTemplate.getForObject(apiUrl, String.class);

        try {
            List<Map<String, Object>> mapRes = new ArrayList<>();
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode dataNode = jsonNode.get("Data");
            JsonNode hourlyDataNode = dataNode.get("Data");


            for (JsonNode node : hourlyDataNode) {  
                long fecha = node.get("time").asLong();
                Instant instant = Instant.ofEpochSecond(fecha);
                LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                double price = node.get("close").asDouble();
                Map<String, Object> map = new HashMap<>();
                map.put("fecha", dateTime);
                map.put("price", price);
                mapRes.add(map);
            }
            message.put(MSG_SUCCESS, Boolean.TRUE);
            message.put("datos", mapRes);
            return message;
        } catch (Exception e) {
            // Manejar excepciones
            message.put(MSG_SUCCESS, Boolean.FALSE);
            return message;
        }
    }
   
}
