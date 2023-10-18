package com.vizzibl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProgramStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Long;

    private String name;

    private int count;

    private Long programId;

    public ProgramStatistic() {
    }

}
