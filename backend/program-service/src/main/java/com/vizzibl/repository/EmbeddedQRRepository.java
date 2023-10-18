package com.vizzibl.repository;

import com.vizzibl.entity.EmbeddedQRModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmbeddedQRRepository extends JpaRepository<EmbeddedQRModel, Long> {


    Optional<EmbeddedQRModel> findByTenantIdAndProgramIdAndStudentId(String tenantId, Long programId, Long studentId);

    Optional<EmbeddedQRModel> findByPublicViewId(String publicViewId);

    Optional<EmbeddedQRModel> findByTenantId(String publicViewId);


}
