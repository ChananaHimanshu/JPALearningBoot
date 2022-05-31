package com.example.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DatabaseConfig {

	protected PropConfig propConfig;

	@Autowired
	public DatabaseConfig(PropConfig propConfig) {
		this.propConfig = propConfig;
	}

	/**
	 * Instantiate the Bean of DataSource to getting a connection.
	 * 
	 * @return DataSource returns the DriverManagerDataSource object
	 */
	// @Bean
	// public DataSource dataSource() {
	// System.out.println("[BEAN] : datasource ---------------------");
	// DriverManagerDataSource dataSource = new DriverManagerDataSource();
	// dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	// dataSource.setUrl("jdbc:mysql://localhost:3306/java_learning");
	// dataSource.setUsername("root");
	// dataSource.setPassword("root");
	// return dataSource;
	// }

	@Bean
	public DataSource dataSource() {
		System.out.println("[BEAN] : datasource ---------------------");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(propConfig.getDatabaseDriver());
		dataSource.setUrl(propConfig.getDatabaseUrl());
		dataSource.setUsername(propConfig.getDatabaseUsername());
		dataSource.setPassword(propConfig.getDatabasePassword());
		return dataSource;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(propConfig.isShowSql());
		hibernateJpaVendorAdapter.setGenerateDdl(propConfig.isGenerateDdl());
		hibernateJpaVendorAdapter.setDatabase(propConfig.getVendor());
		return hibernateJpaVendorAdapter;
	}

	@Bean
	public RestTemplate restTemplate() {
		System.out.println("[BEAN] : rest-template ---------------------");
		RestTemplate temp = new RestTemplate();
		System.out.println("This bean : " + temp);
		return temp;
	}

	// @Bean(name = "restResolverTemplate")
	@Bean
	@Primary
	public RestTemplate getRestResolverTemplate() {
		System.out.println("[BEAN] : ----------------------------test--------------");
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("RestResolverTemplate bean initialized {" + restTemplate + "}");
		return restTemplate;
	}

}
