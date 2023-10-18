package com.vizzibl.entity.assertion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assertion {

    private String type;
    private String id;
    @JsonProperty("@context")
    private String context;
    private AssertionRecipient recipient;
    private Badge badge;
    private Date issuedOn;
    private AssertionImage image;
    private boolean revoked;
    private AssertionVerification verification;

}
