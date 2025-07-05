package com.lithanarianaren.notavito;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import lombok.extern.java.Log;

import javax.sql.DataSource;

@SpringBootApplication
public class NotavitoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotavitoApplication.class, args);
	}

}
