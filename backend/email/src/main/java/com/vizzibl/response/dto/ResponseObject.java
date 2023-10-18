package com.vizzibl.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject {

    private Integer code;
    private String message;
    private Object data;

    public ResponseObject(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseObject(Integer code, Object response) {
        this.code = code;
        this.data = response;
    }
}
