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
public class Badge {

    @JsonProperty(value = "@context", index = 0)
    private String context;
    private String type;
    private String id;
    private String name;
    private String description;
    private String image;
    private Criteria criteria;
    private Issuer issuer;

}
