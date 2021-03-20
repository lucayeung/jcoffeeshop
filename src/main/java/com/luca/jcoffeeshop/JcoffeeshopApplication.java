package com.luca.jcoffeeshop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class JcoffeeshopApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(JcoffeeshopApplication.class, args);
	}

	@Override
	public void run(String... args) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM t_product");
		list.forEach(s -> log.info("Category: {}", s));
	}
}
