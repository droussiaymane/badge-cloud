package com.vizzibl.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class EmbeddedQRModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String publicViewId;
    private Long studentId;
    private Long programId;
    private Long badgeId;
    private String fileName;
    private String tenantId;

    public EmbeddedQRModel() {
    }

}
