package com.vizzibl.controller.velocity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vizzibl.dto.UserDTO;
import com.vizzibl.dto.velocity.BadgeResponse;
import com.vizzibl.dto.velocity.Credential;
import com.vizzibl.dto.velocity.CredentialSubject;
import com.vizzibl.dto.velocity.OfferRequest;
import com.vizzibl.entity.StudentProgram;
import com.vizzibl.proxy.DigitalWalletServiceProxy;
import com.vizzibl.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Service
public class VelocityBadgesService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DigitalWalletServiceProxy digitalWalletServiceProxy;

    @Value("${badges.image.path}")
    private String badgeImagesPath;
    @Autowired
    private Environment environment;


    public String createOffer(String tenantId, StudentProgram studentProgram) {
        String url = "http://217.76.55.249:8083/operator-api/v0.8/tenants/did:ion:EiAOJzQn8mOU7jofLEXfROxG_oR9Fc_JeXs1KzonGNaHpQ/exchanges/645176420721896611d8371a/offers";


        ResponseEntity<ResponseObject> studentByUserId = digitalWalletServiceProxy.findByUserId(tenantId, null, studentProgram.getStudentId().toString());
        UserDTO userDTO = null;
        if (studentByUserId.getBody() != null && studentByUserId.getBody().getCode() == 1) {
            userDTO = new ObjectMapper().convertValue(
                    studentByUserId.getBody().getData(),
                    new TypeReference<>() {
                    });

        }

        if (userDTO == null) {
            System.out.println("User not found");
            return null;
        }

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoidmVsb2NpdHkuYWRtaW5AZXhhbXBsZS5jb20ifQ.vssTp--KThrqP0-E7BOpXfTneXmQUYcv0elKAcXhAVg");

        OfferRequest request = new OfferRequest();
        request.setType(Collections.singletonList("OpenBadgeV2.0"));
        request.setOfferCreationDate(LocalDateTime.now().atOffset(ZoneOffset.UTC).format(dtf));
        request.setOfferExpirationDate(LocalDateTime.now().plusYears(1).atOffset(ZoneOffset.UTC).format(dtf));
        request.setOfferId("ptIQrgxaicFX0QPVf_Z1L");
        request.setOfferId("ptIQrgxaicFX0QPVf_Z1L");

        Credential credential = new Credential();
        credential.setId("https://example.com/velocity-badge.json");
        credential.setType("BadgeClass");
        credential.setName(studentProgram.getProgram().getProgramName());
        credential.setDescription(studentProgram.getProgram().getDescription());
        credential.setImage(environment.getProperty("file-server.base-url") + badgeImagesPath + "/" + tenantId + "/" + studentProgram.getProgram().getProgramBadge().getBadgesModel().getFileName());
        credential.setCriteria("https://example.com/velocity-badge.html");
        credential.setIssuer("did:ion:EiD0zTrc7bZAQlM7MUkzkNyhPzWQRwMsHOyXtSStE0FEhw");

        CredentialSubject credentialSubject = new CredentialSubject();
        credentialSubject.setVendorUserId(userDTO.getEmail());
        credentialSubject.setHasCredential(credential);

        request.setCredentialSubject(credentialSubject);

        HttpEntity<OfferRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<BadgeResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, BadgeResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Offer created successfully");

            BadgeResponse badgeResponse = response.getBody();
            if (badgeResponse != null) {
                String id = badgeResponse.getId();
                System.out.println("TEST ===================> id : " + id);
                return id;
            }

        } else {
            System.out.println("Error creating offer");
            return null;
        }
        return null;
    }
}
