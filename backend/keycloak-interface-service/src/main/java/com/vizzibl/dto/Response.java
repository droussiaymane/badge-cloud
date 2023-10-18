package com.vizzibl.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Response {

    private Boolean isSuccess;
    private String msg;
    private Object data;
}
