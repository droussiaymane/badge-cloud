package com.vizzibl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentHomeDTO {

    private Long id;
    private String studentName;
    private String studentId;
    private String Image;

    private List<ProgramBadgeDTO> programsBadges;


}
