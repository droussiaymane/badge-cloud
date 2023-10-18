package com.vizzibl.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class StudentCollections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "badge_id", nullable = false)
    private BadgesModel badgesModel;

/*    @ManyToOne
    @JoinColumn(name="student_program_id", nullable=false)
    private StudentProgram studentProgram;*/

    public StudentCollections() {
    }

}
