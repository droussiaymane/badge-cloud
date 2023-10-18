package com.vizzibl.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class KeycloakResponseObject {
    private Integer code;
    private String message;
    private String kID;

    public KeycloakResponseObject(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public KeycloakResponseObject(Integer code, String message, String kID) {
        this.code = code;
        this.message = message;
        this.kID = kID;
    }
}
