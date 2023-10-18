package com.vizzibl.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class InstructorProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private ProgramsModel program;

    private Long instructorId;

    public InstructorProgram() {
    }


}
