package com.vizzibl.entity.assertion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssertionExtensionsRecipientProfile {
    @JsonProperty(value = "@context", index = 0)
    private String context;
    private ArrayList<String> type;
    private String name;
}
