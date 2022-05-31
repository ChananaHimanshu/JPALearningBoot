package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

@SuppressWarnings("deprecation")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private AuthenticationManager authenticationManager;
	private DefaultTokenServices defaultTokenServices;
	private PropConfig propConfig;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public AuthorizationServerConfig(AuthenticationManager authenticationManager,
			DefaultTokenServices defaultTokenServices, PropConfig propConfig, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.defaultTokenServices = defaultTokenServices;
		this.propConfig = propConfig;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(propConfig.getWebAppClientId()).secret(passwordEncoder.encode(propConfig.getWebAppApiSecret()))
				.authorities("CLIENT", "TRUSTED_CLIENT", "ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
				.scopes("read", "write", "trust")
				.authorizedGrantTypes("password", "authorization_code", "implicit", "client_credentials");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenServices(defaultTokenServices).authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("hasAuthority('SERVICE')");
	}
}
