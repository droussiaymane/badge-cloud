package com.vizzibl.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class BadgesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String badgeName;
    private String createdBy;


    @OneToOne(mappedBy = "badgesModel", fetch = FetchType.EAGER)
    private ProgramBadge programBadge;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private String fileName;
    @OneToMany(mappedBy = "badgesModel")
    private List<StudentCollections> studentCollections;
    private String tenantId;
    @Column(nullable = true)
    private String photos;

    public BadgesModel() {
    }

    public BadgesModel(Long id) {
        this.id = id;
    }

}
