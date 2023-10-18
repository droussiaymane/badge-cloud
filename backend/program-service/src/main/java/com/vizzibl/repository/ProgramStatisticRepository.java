package com.vizzibl.repository;


import com.vizzibl.entity.ProgramStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramStatisticRepository extends JpaRepository<ProgramStatistic, Long> {

    List<ProgramStatistic> findAll();

    @Query(value = "select ps from ProgramStatistic ps where ps.programId= :programId")
    ProgramStatistic findByProgramId(Long programId);

}
