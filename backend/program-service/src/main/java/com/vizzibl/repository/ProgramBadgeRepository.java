package com.vizzibl.repository;


import com.vizzibl.entity.BadgesModel;
import com.vizzibl.entity.ProgramBadge;
import com.vizzibl.entity.ProgramsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramBadgeRepository extends JpaRepository<ProgramBadge, Long> {
    List<ProgramBadge> findAllByBadgesModel(BadgesModel badgesModel);

    ProgramBadge deleteAllByBadgesModel(BadgesModel badgesModel);

    ProgramBadge findByProgramsModel(ProgramsModel programsModel);

    List<ProgramBadge> findAll();
}
