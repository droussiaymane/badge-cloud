package com.vizzibl.repository;


import com.vizzibl.entity.ProgramCompetency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramCompetencyRepository extends CrudRepository<ProgramCompetency, Long> {

    List<ProgramCompetency> findAllByCompetencyIdAndProgramsModelId(int id, int id1);

    void deleteAllByProgramsModelId(int id);


    Optional<ProgramCompetency> findByCompetency_IdAndProgramsModel_Id(Long competencyId, Long programId);
}
