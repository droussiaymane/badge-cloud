package com.vizzibl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgramCompetency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "competency_id", nullable = true)
    private Competency competency;

    @ManyToOne
    @JoinColumn(name = "program_model_id", nullable = true)
    private ProgramsModel programsModel;

    public ProgramCompetency(Competency competency, ProgramsModel programsModel) {
        this.competency = competency;
        this.programsModel = programsModel;
    }
}
