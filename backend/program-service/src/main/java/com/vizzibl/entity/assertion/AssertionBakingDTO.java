package com.vizzibl.entity.assertion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssertionBakingDTO {
    @JsonProperty(value = "@context", index = 0)
    private String context;
    private String type;
    private String id;
    private AssertionRecipient recipient;
    private String badge;
    private String issuedOn;
    private String image;
    private String expires;
    private boolean revoked;
    private AssertionVerification verification;
}
