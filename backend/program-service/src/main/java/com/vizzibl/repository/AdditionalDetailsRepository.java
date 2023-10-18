package com.vizzibl.repository;


import com.vizzibl.entity.AdditionalDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdditionalDetailsRepository extends JpaRepository<AdditionalDetails, Long> {

    @Query(value = "select ad from AdditionalDetails ad where ad.tenantId= :tenantId and ad.id not" +
            " in(select pm.additionalDetails.id from ProgramsModel pm where pm.tenantId = :tenantId) ")
    List<AdditionalDetails> getAllUnmappedDetailsByTenantId(String tenantId);

    Page<AdditionalDetails> findAllByTenantId(String tenantId, Pageable pageable);

    @Query(value = "select ad from AdditionalDetails ad where ad.tenantId= :tenantId and  ad.name LIKE CONCAT('%',:name,'%')")
    List<AdditionalDetails> findAllByTenantIdAndName(String tenantId, String name);
}
