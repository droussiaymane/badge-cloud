package com.vizzibl.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
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
