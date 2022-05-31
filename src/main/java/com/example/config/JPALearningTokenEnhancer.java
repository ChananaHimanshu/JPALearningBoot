package com.example.config;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Component
public class JPALearningTokenEnhancer implements TokenEnhancer {

	@Autowired
	private PropConfig propConfig;

	/**
	 * Enhance the user's access token with his id.
	 * 
	 * If user's TFA is enabled, access token will not return until user passes the
	 * all the challenges. Google Authenticator implements two-step verification
	 * services using the Time-based One-time Password Algorithm (TOTP) and
	 * HMAC-based One-time Password Algorithm (HOTP), for authenticating users of
	 * mobile applications by Google. The service implements algorithms specified in
	 * RFC 6238 and RFC 4226.
	 *
	 * @param accessToken    the user's access token
	 * @param authentication the user's access authentication
	 * @return OAuth2AccessToken with the user's access token
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
		JPALearningUserDetails userDetails = (JPALearningUserDetails) authentication.getUserAuthentication()
				.getPrincipal();
		Long id = userDetails.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", (Object) id);
		map.put("username", (Object) userDetails.getUsername());
		result.setAdditionalInformation(map);
		// result.setAdditionalInformation(Collections.singletonMap("user_id", (Object) id));
		// userDetails.getUsername()));
		return result;
	}

	/**
	 * Reset the user's access token to an empty string and setting its expire time
	 * to the current date-time
	 * 
	 * @param result DefaultOAuth2AccessToken instance
	 * 
	 * @return DefaultOAuth2AccessToken instance
	 */
	private DefaultOAuth2AccessToken resetAccesToken(DefaultOAuth2AccessToken result) {
		result.setValue("");
		result.setExpiration(new Date());
		return result;
	}

}