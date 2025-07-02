package com.lithanarianaren.notavito;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import lombok.extern.java.Log;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class NotavitoApplication implements CommandLineRunner {

	private final DataSource dataSource;

	public NotavitoApplication (final DataSource dataSource) {this.dataSource = dataSource;}

	public static void main(String[] args) {
		SpringApplication.run(NotavitoApplication.class, args);
	}

	public void run(final String... args) {
		log.info("Datasource: " + dataSource.toString());
		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
		restTemplate.execute("select 1");
	}

}
