package com.vizzibl.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProgramBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "program_id", nullable = true)
    private ProgramsModel programsModel;

    @OneToOne
    @JoinColumn(name = "badge_id", nullable = true)
    private BadgesModel badgesModel;

    public ProgramBadge() {
    }

}
