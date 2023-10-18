package com.vizzibl.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class JwtParserUtil {

    private Map<String, Object> jsonMap;
    private final ObjectMapper mapper;

    public JwtParserUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public HeaderMapRequestWrapper JwtParser(HttpServletRequest request) {
        HeaderMapRequestWrapper wrappedRequest = new HeaderMapRequestWrapper(request);
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken != null) {

            //------------ Decode JWT ------------
            String[] split_string = jwtToken.split("\\.");
            //String base64EncodedHeader = split_string[0];
            String base64EncodedBody = split_string[1];
            //String base64EncodedSignature = split_string[2];

            //~~~~~~~~~ JWT Header ~~~~~~~
            Base64 base64Url = new Base64(true);
            //String header = new String(base64Url.decode(base64EncodedHeader));

            //~~~~~~~~~ JWT Body ~~~~~~~
            String body = new String(base64Url.decode(base64EncodedBody));

            try {
                // convert JSON string to Map
                this.jsonMap = mapper.readValue(body,
                        new TypeReference<>() {
                        });
            } catch (Exception ex) {
//                throw new RuntimeException(ex);
                return wrappedRequest;
            }

            System.out.println("Filter : " + this.jsonMap.get("tenantId"));

            if (this.jsonMap.get("tenantId") == null) {
                wrappedRequest.addHeader("tenantId", (String) this.jsonMap.get("tenantId"));
            }
            if (this.jsonMap.get("userId") != null) {
                wrappedRequest.addHeader("userId", (String) this.jsonMap.get("userId"));
            }
        }
        try {

            if (request.getCookies() != null) {
                for (int i = 0; i < request.getCookies().length; i++) {
                    if (request.getCookies()[i].getName().equals("Authorization")) {
                        wrappedRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + request.getCookies()[i].getValue());

                    }
                }
            }
        } catch (Exception e) {
            //log.error("Exception in PerRequestFilter ", e);
            return wrappedRequest;
        }
        return wrappedRequest;
    }


}
