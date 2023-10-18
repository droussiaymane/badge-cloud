package com.vizzibl.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class StudentProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "program_id", nullable = false)
    private ProgramsModel program;
    private Long studentId;
    private int status;

    private String wallet = "native wallet";

    public StudentProgram() {
        this.wallet = "native wallet";
    }
}
