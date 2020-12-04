//package com.iteye.wwwcomy.appdemo;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//
//public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {
//	@Override
//	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//		Map<String, Object> additionalInfo = new HashMap<>();
//		additionalInfo.put("subject", authentication.getPrincipal().toString());
//		accessToken.setAdditionalInformation(additionalInfo);
//		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) super.enhance(accessToken, authentication);
//		return token;
//	}
//}
