package com.spring.universidad.cryptop2p;

import com.spring.universidad.cryptop2p.modelo.entidades.Crypto;
import com.spring.universidad.cryptop2p.modelo.entidades.numeradores.CryptoEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import services.BinanceService;

@SpringBootApplication
public class UniversidadBackendApplication {


	public static void main(String[] args) {
		Crypto crypto = new Crypto();
		System.out.println(crypto.getInfo(CryptoEnum.BTCUSDT));
		String[] str = SpringApplication.run(UniversidadBackendApplication.class, args).getBeanDefinitionNames();
	}

}
