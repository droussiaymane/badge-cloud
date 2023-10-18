package com.vizzibl.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class StudentProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

/*    @ManyToOne
    @JoinColumn(name="program_id", nullable=false)
    private ProgramsModel program;*/

    private Long programId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private UsersModel student;

    @JsonIgnore
    @OneToMany(mappedBy = "studentProgram", cascade = CascadeType.REMOVE)
    private List<StudentCollections> studentCollections;

    private int status;

    public StudentProgram() {
    }

}
