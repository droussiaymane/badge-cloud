package com.vizzibl.entity.assertion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Issuer {
    @JsonProperty(value = "@context", index = 0)
    private String context;
    private String id;
    private String type;
    private String name;
    private String url;
    private String email;
    private Verification verification;
}
