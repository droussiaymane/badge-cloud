package com.vizzibl.dto.velocity;

import lombok.Data;

@Data
public class Credential {
    private String id;
    private String type;
    private String name;
    private String description;
    private String image;
    private String criteria;
    private String issuer;
}
