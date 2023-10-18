package com.vizzibl.repository;


import com.vizzibl.entity.AdditionalDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramAdditionalDetailsRepository extends CrudRepository<AdditionalDetails, Long> {

    List<AdditionalDetails> findAll();

    Optional<AdditionalDetails> findByIdAndTenantId(Long id, String tenantId);

    @Query(value = "select ad from AdditionalDetails ad where ad.tenantId= :tenantId and ad.name LIKE CONCAT('%',:name,'%')")
    List<AdditionalDetails> findAllByTenantIdAndName(String tenantId, String name);
}
