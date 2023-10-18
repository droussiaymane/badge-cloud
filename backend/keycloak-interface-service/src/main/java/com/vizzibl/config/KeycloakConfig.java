package com.vizzibl.config;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class KeycloakConfig {

    @Value("${admin.realm}")
    private String realm;

    @Value("${admin.clientId}")
    private String clientId;

    @Value("${admin.clientSecret}")
    private String clientSecret;

    @Value("${admin.serverUrl}")
    private String serverUrl;

    @Bean
    public Keycloak keycloak() {
        log.info("realm : {} , clientId: {} , secret : {}", realm, clientId, clientSecret);
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .serverUrl(serverUrl)
                .resteasyClient(
                        new ResteasyClientBuilderImpl()
                                .connectionPoolSize(10).build())
                .build();

    }

    @Bean
    public Keycloak keycloakLogin() {
        log.info("realm : {} , clientId: {} , secret : {}", realm, clientId, clientSecret);
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .serverUrl(serverUrl)
                .resteasyClient(
                        new ResteasyClientBuilderImpl()
                                .connectionPoolSize(10).build())
                .build();
    }

    public RealmResource getKeycloakInstance() {
        return keycloak().realm(realm);
    }
}
