package com.vizzibl.request.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BadgeEmailDetailsDto {


    String firstName;
    String lastName;
    String program;
    String issuerName;
    String to;
    String createdBy;


}
