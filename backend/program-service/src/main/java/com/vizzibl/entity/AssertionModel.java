package com.vizzibl.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class AssertionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime issuedOn;
    @Column(unique = true)
    private String assertionKey;

    @OneToOne
    @JoinColumn(name = "open_badge_mapping_model")
    private OpenBadgeMappingModel openBadgeMappingModel;

}
