package com.hexagon.hrproject.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class OAuth2ClientConfig {

	// 구글 클라이언트 ID·시크릿은 config/google.local.properties에서 읽는다
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository(Environment environment) {
		ClientRegistration google = CommonOAuth2Provider.GOOGLE.getBuilder("google")
				.clientId(environment.getRequiredProperty("google.client-id"))
				.clientSecret(environment.getRequiredProperty("google.client-secret"))
				.scope("openid", "profile", "email")
				.build();

		return new InMemoryClientRegistrationRepository(google);
	}

	@Bean
	public OAuth2AuthorizedClientService authorizedClientService(
			ClientRegistrationRepository clientRegistrationRepository) {
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
	}
}
