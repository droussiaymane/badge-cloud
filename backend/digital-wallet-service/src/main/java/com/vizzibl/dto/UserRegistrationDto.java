package com.vizzibl.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegistrationDto {

    String firstName;
    String lastName;
    String program;
    String userName;
    String password;
    String to;


}
