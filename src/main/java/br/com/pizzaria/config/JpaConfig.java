package br.com.pizzaria.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class JpaConfig {
	
	
	@Bean
	public DataSource dataSource() throws PropertyVetoException {
		HikariConfig hikariConfig = new HikariConfig();
		
		hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
		hikariConfig.setUsername("root");
		hikariConfig.setPassword("");
		hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/pizzaria");
		hikariConfig.setMinimumIdle(10);
		hikariConfig.setMaximumPoolSize(20);
		hikariConfig.setMaxLifetime(1200000);
		
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		
		return dataSource;
	}
	
}
