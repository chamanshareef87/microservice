package com.in28minutes.microservices;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private Environment environment;
	@Autowired
	private ExchangeRepository repository;
	
	public ExchangeRepository getRepository() {
		return repository;
	}

	public void setRepository(ExchangeRepository repository) {
		this.repository = repository;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retriveExchangeValue(@PathVariable String from,
			@PathVariable String to) {
//		ExchangeValue exchange = new ExchangeValue(1000L, from, to, BigDecimal.valueOf(65));
		ExchangeValue exchange = repository.findByFromAndTo(from, to);
		
		LOG.info("Exchange:::{}",exchange);

		exchange.setPort(Integer.valueOf(environment.getProperty("server.port")));
		return exchange;
		
	}
}
