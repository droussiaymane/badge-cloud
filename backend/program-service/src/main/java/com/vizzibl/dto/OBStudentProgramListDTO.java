package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OBStudentProgramListDTO {

    private String tenantId;
    private Long studentId;
    private String studentName;
    private Long programId;
    private boolean revokeStatus;
    private String revokeReason;
    private String wallet;
    private boolean expiresStatus;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "dd-MM-uuuu")
    private LocalDateTime expires;
    private int status;
    private Long badgeId;
    private String fileName;


}
