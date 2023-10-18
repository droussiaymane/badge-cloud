package com.vizzibl.repository;


import com.vizzibl.entity.Evidence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {
    List<Evidence> findAll();


    Optional<Evidence> findByIdAndTenantId(Long id, String tenantId);

    Page<Evidence> findAllByTenantId(String tenantId, Pageable pageable);

    @Query(value = "select evidence from Evidence evidence where evidence.tenantId= :tenantId and evidence.name LIKE CONCAT('%',:name,'%')")
    List<Evidence> findAllByTenantIdAndName(String tenantId, String name);

    @Query(value = "select evidence from Evidence evidence where evidence.tenantId= :tenantId and evidence.id not " +
            " in(select program.evidence.id from ProgramsModel program where program.tenantId =: tenantId) ")
    List<Evidence> findAllUnmappedEntriesByTenant(String tenantId);
}
