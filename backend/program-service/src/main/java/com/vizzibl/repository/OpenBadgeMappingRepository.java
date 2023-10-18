package com.vizzibl.repository;

import com.vizzibl.entity.OpenBadgeMappingModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenBadgeMappingRepository extends JpaRepository<OpenBadgeMappingModel, Long> {

    Optional<OpenBadgeMappingModel> findByTenantIdAndProgramIdAndStudentId(String tenantId, Long programId, Long studentId);
//    List<OpenBadgeMappingModel> findAllByTenantIdAndProgramIdAndStudentId(String tenantId,Long programId,Long studentId);
}
