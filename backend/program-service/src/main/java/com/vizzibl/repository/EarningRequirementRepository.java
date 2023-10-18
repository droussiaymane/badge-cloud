package com.vizzibl.repository;


import com.vizzibl.entity.EarningRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EarningRequirementRepository extends JpaRepository<EarningRequirement, Long> {


    Optional<EarningRequirement> findByIdAndTenantId(Long id, String tenantId);

    @Query(value = "select er from EarningRequirement er where er.tenantId = :tenantId and er.id not " +
            " in(select p.earningRequirement.id from ProgramsModel  p where p.tenantId=:tenantId)")
    List<EarningRequirement> findAllUnmappedEntriesByTenantId(String tenantId);

    Page<EarningRequirement> findAllByTenantId(String tenantId, Pageable pageable);

    @Query(value = "select er from EarningRequirement er where er.tenantId = :tenantId and  er.name LIKE CONCAT('%',:name,'%')")
    List<EarningRequirement> findAllByTenantIdAndName(String tenantId, String name);
}
