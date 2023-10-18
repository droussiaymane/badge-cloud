package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String role;
    private String tenantId;
}
