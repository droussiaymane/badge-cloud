package com.vizzibl.repository;


import com.vizzibl.entity.Competency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetenciesRepository extends JpaRepository<Competency, Long> {

    List<Competency> findAll();

    List<Competency> findAllByIdIn(List<Long> competencies);

    @Query(value = "select comp from Competency comp where comp.tenantId = :tenantId and comp.id not in" +
            "(select pc.competency.id from ProgramCompetency pc where pc.competency.tenantId = :tenantId) ")
    List<Competency> findAllUnmappedEntriesByTenantId(String tenantId);

    Page<Competency> findAllByTenantId(String tenantId, Pageable pageable);

    @Query(value = "select comp from Competency comp where comp.tenantId = :tenantId and comp.name LIKE CONCAT('%',:name,'%')")
    List<Competency> findByTenantIdAndName(String tenantId, String name);
}
