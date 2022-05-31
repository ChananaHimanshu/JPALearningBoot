package com.example.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.util.unit.DataSize;

@Configuration
public class InitialBeanConfig {

	/**
	 * Instantiate the Bean of TokenEnhancer to enhance a token.
	 * 
	 * @return TokenEnhancer instance
	 */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new JPALearningTokenEnhancer();
	}

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	/**
	 * Instantiate the Bean of DefaultTokenServices.
	 * 
	 * @return DefaultTokenServices instance
	 */
	@Primary
	@Bean
	public DefaultTokenServices defaultTokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setReuseRefreshToken(false);
		return defaultTokenServices;
	}

	/**
	 * Instantiate the Bean of MultipartConfigElement
	 * 
	 * @return the MultipartConfigElement object
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofMegabytes(10));
		factory.setMaxRequestSize(DataSize.ofMegabytes(10));
		return factory.createMultipartConfig();
	}

}
