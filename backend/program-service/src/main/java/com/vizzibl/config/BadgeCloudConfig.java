package com.vizzibl.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "badge-cloud")
public class BadgeCloudConfig {

    public BadgeCloudConfig() {
    }

    private String context;
    private String assertionUrl;
    private String badgeUrl;
    private String issuerName;
    private String issuerUrl;
    private String issuerEmail;
    private String issuerId;
    private String issuerType;

}
