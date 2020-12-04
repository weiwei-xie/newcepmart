package com.iteye.wwwcomy.appdemo;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;

@SpringBootApplication
@EnableAuthorizationServer
@EnableWebSecurity
@EnableResourceServer
public class AuthService {

	public static void main(String[] args) {
		SpringApplication.run(AuthService.class, args);
	}

}

/**
 *
 * Really don't understand why I should use ACCESS_OVERRIDE_ORDER to make it
 * work
 * 
 * @see https://stackoverflow.com/questions/49970346/correctly-configure-spring-security-oauth2
 * @see https://github.com/spring-projects/spring-security-oauth/issues/980
 * 
 *      From what I understand, using spring security OAuth2, multiple
 *      configurations are done to protect different endpoints, like authorize
 *      endpoint, user endpoint, token endpoint, login endpoint.
 * 
 *      Such kind of endpoints are actually defined in different configurations
 *      with different filters enabled, which, might have conflicts.
 * 
 *      That's why it is so hard to make it work...
 * @author xingnliu
 */
@Configuration
//@Order(-20)
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				// .loginPage("/login") // This is for custom login page, although it is the
				// same URL with the out-of-box login page
				.and().authorizeRequests().antMatchers("/login*", "/oauth/token_key", "/.well-known/jwks.json")
				.permitAll()
				//
				.anyRequest().authenticated().and().csrf().disable();
	}

}

@Configuration
class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId("USERS");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatchers().antMatchers("/api/**").and().authorizeRequests().anyRequest().authenticated();
	}
}

@Configuration
class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("m1").secret("{noop}s1")
				.authorizedGrantTypes("authorization_code", "refresh_token", "password", "client_credentials")
				.scopes("test_scope1").redirectUris("http://localhost:8089/login").autoApprove(true)
				//
				.and().withClient("m2").secret("{noop}s2")
				.authorizedGrantTypes("authorization_code", "refresh_token", "password", "client_credentials")
				.scopes("test_scope2").redirectUris("http://wwwcomy-pc:8090/login/oauth2/code/test").autoApprove(true)
				// the scope openid is for OIDC, there's special configuration needed in
				// OidcAuthorizationCodeAuthenticationProvider (ID Token needed)
				.and().withClient("m3").secret("{noop}s3")
				.authorizedGrantTypes("authorization_code", "refresh_token", "password", "client_credentials")
				.scopes("test_scope3").redirectUris("http://localhost:9998/login").autoApprove(true);
	}

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices services = new DefaultTokenServices();
		services.setTokenStore(tokenStore());
		TokenEnhancerChain chain = new TokenEnhancerChain();
		chain.setTokenEnhancers(List.of(tokenEnhancer(), jwtTokenConverter()));
		services.setTokenEnhancer(chain);
		services.setSupportRefreshToken(true);
		services.setClientDetailsService(clientDetailsService);
		return services;
	}

	private TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			if (authentication != null) {
				Map<String, Object> additionalInfo = new HashMap<>();
				additionalInfo.put("subject", authentication.getPrincipal().toString());
				((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			}
			return accessToken;
		};
	}

	@Bean
	public TokenStore tokenStore() {
//		return new InMemoryTokenStore();
		return new JwtTokenStore(jwtTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter jwtTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setKeyPair(keyPair());
		return converter;
	}

	@Bean
	public JWKSet jwkSet() {
		RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic()).keyUse(KeyUse.SIGNATURE)
				.algorithm(JWSAlgorithm.RS256).keyID("test-id");
		return new JWKSet(builder.build());
	}

	@Bean
	public KeyPair keyPair() {
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"),
				"passwd".toCharArray());
		return keyStoreKeyFactory.getKeyPair("mytest");
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//		oauthServer.allowFormAuthenticationForClients();
		oauthServer.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()")
		// .checkTokenAccess("permitAll()")
		;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenServices(tokenServices()).tokenStore(tokenStore());
	}

}