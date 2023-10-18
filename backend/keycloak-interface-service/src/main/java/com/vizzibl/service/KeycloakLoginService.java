package com.vizzibl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vizzibl.dto.LoginDto;
import com.vizzibl.dto.RefreshTokenDto;
import com.vizzibl.response.ConstantUtil;
import com.vizzibl.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class KeycloakLoginService {

    @Value("${admin.realm}")
    private String realm;

    @Value("${admin.clientId}")
    private String clientId;

    @Value("${admin.clientSecret}")
    private String clientSecret;

    @Value("${admin.serverUrl}")
    private String serverUrl;

    @Autowired
    KeycloakAdminService KeycloakAdminService;

    public ResponseObject login(LoginDto loginDto) throws JsonProcessingException {
        ResponseObject response = null;
        AccessTokenResponse accessTokenResponse = null;
        try {
            log.info("realm : {} , clientId: {} , secret : {}", realm, clientId, clientSecret);
            Keycloak keycloak = KeycloakBuilder.builder()
                    .grantType(OAuth2Constants.PASSWORD)
                    .realm(realm)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .username(loginDto.getUsername())
                    .password(loginDto.getPassword())
                    .serverUrl(serverUrl)
                    .resteasyClient(
                            new ResteasyClientBuilderImpl()
                                    .connectionPoolSize(10).build())
                    .build();
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            response = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success!", accessTokenResponse);
            log.info("Entity :", accessTokenResponse.getError());

        } catch (Exception e) {
            log.error("Error", e);
            if (KeycloakAdminService.sendVerificationEmail(loginDto.getUsername())) {
                response = new ResponseObject(ConstantUtil.ERROR_CODE, "Email for verification is sent to your registered email address. Please verify before continue.");
            } else {
                response = new ResponseObject(ConstantUtil.ERROR_CODE, e.getMessage());
            }
        }
        return response;
    }

    public ResponseObject refreshToken(RefreshTokenDto refreshTokenDto) throws JsonProcessingException {
        ResponseObject response = null;
        ResponseEntity<Object> res = null;
        AccessTokenResponse accessTokenResponse = null;
        String url = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info("realm : {} , clientId: {} , secret : {}", realm, clientId, clientSecret);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type", "refresh_token");
            map.add("refresh_token", refreshTokenDto.getRefresh_token());
            map.add("client_id", clientId);
            map.add("client_secret", clientSecret);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

            res = restTemplate.exchange(url,
                    HttpMethod.POST,
                    entity,
                    Object.class);

            response = new ResponseObject(ConstantUtil.SUCCESS_CODE, "success", res.getBody());

        } catch (Exception err) {
            log.error("error" + err.getMessage());
            response = new ResponseObject(ConstantUtil.ERROR_CODE, err.getMessage());
        }
        return response;
    }
}
