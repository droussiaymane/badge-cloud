package com.vizzibl.repository;


import com.vizzibl.entity.ProgramStatisticInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramStatisticInstructorRepository extends JpaRepository<ProgramStatisticInstructor, Long> {

    List<ProgramStatisticInstructor> findAll();

    @Query(value = "select ps from ProgramStatisticInstructor ps where ps.programId = :programId")
    ProgramStatisticInstructor findByProgramId(Long programId);
}
