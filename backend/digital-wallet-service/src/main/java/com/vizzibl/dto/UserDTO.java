package com.vizzibl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {

    private Long id;
    private String tenantId;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String category;
    private String photo;
}
