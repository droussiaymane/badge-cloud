package com.vizzibl.repository;


import com.vizzibl.entity.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StandardRepository extends JpaRepository<Standard, Long> {
    List<Standard> findAll();

    Optional<Standard> findByIdAndTenantId(Long id, String tenantId);

    Page<Standard> findAllByTenantId(String tenantId, Pageable pageable);


    @Query(value = "select s from Standard s where s.tenantId = :tenantId and s.name LIKE CONCAT('%',:name,'%')")
    List<Standard> findByTenantIdAndName(String tenantId, String name);

    @Query(value = " select s from Standard s where s.tenantId = :tenantId and s.id not in " +
            " (select p.standard.id from ProgramsModel p where p.tenantId = :tenantId )")
    List<Standard> findUnmappedByTenantId(String tenantId);
}
