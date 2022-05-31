package com.example.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.stereotype.Component;

//value = "file:${user.home}/Teamium/config/application.properties"
@Component
@PropertySources({
		@PropertySource(value = "file:${user.home}/eclipse-workspace-learning/JPALearningBoot/src/main/resources/application.properties", ignoreResourceNotFound = false) })
public class PropConfig implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/**
	 * The database Driver.
	 */
	@Value("${database.driver}")
	private String databaseDriver;

	/**
	 * The database URL.
	 */
	@Value("${database.url}")
	private String databaseUrl;

	/**
	 * The database UserName.
	 */
	@Value("${database.username}")
	private String databaseUsername;

	/**
	 * The database password.
	 */
	@Value("${database.password}")
	private String databasePassword;

	/**
	 * The boolean property to whether show SQL or not.
	 */
	@Value("${database.showSql}")
	private boolean showSql;

	/**
	 * The boolean property to whether generate DDL or not.
	 */
	@Value("${database.generateDdl}")
	private boolean generateDdl;

	/**
	 * The database vendor.
	 */
	@Value("${database.vendor}")
	private Database vendor;

	/**
	 * The server port address.
	 */
	@Value("${server.port}")
	private Long serverPort;

	/**
	 * The web client id
	 */
	@Value("${jpalearning.web.client.id}")
	private String webAppClientId;

	/**
	 * The web app secret
	 */
	@Value("${jpalearning.web.api.secret}")
	private String webAppApiSecret;

	public PropConfig() {

	}

	public String getDatabaseDriver() {
		return databaseDriver;
	}

	public void setDatabaseDriver(String databaseDriver) {
		this.databaseDriver = databaseDriver;
	}

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public String getDatabaseUsername() {
		return databaseUsername;
	}

	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	public boolean isShowSql() {
		return showSql;
	}

	public void setShowSql(boolean showSql) {
		this.showSql = showSql;
	}

	public boolean isGenerateDdl() {
		return generateDdl;
	}

	public void setGenerateDdl(boolean generateDdl) {
		this.generateDdl = generateDdl;
	}

	public Database getVendor() {
		return vendor;
	}

	public void setVendor(Database vendor) {
		this.vendor = vendor;
	}

	public Long getServerPort() {
		return serverPort;
	}

	public void setServerPort(Long serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * @return the webAppClientId
	 */
	public String getWebAppClientId() {
		return webAppClientId;
	}

	/**
	 * @param webAppClientId the webAppClientId to set
	 */
	public void setWebAppClientId(String webAppClientId) {
		this.webAppClientId = webAppClientId;
	}

	/**
	 * @return the webAppApiSecret
	 */
	public String getWebAppApiSecret() {
		return webAppApiSecret;
	}

	/**
	 * @param webAppApiSecret the webAppApiSecret to set
	 */
	public void setWebAppApiSecret(String webAppApiSecret) {
		this.webAppApiSecret = webAppApiSecret;
	}

}
