package com.vizzibl.repository;

import com.vizzibl.entity.InstructorProgram;
import com.vizzibl.entity.ProgramsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorProgramRepository extends JpaRepository<InstructorProgram, Integer> {

    List<InstructorProgram> findByProgram(ProgramsModel programsModel);
}
