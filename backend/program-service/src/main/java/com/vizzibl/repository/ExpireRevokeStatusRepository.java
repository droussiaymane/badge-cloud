package com.vizzibl.repository;


import com.vizzibl.entity.ExpireRevokeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpireRevokeStatusRepository extends JpaRepository<ExpireRevokeStatus, Long> {

    List<ExpireRevokeStatus> findByStudentIdAndProgramId(Long studentId, Long programId);

    Optional<ExpireRevokeStatus> findByTenantIdAndStudentIdAndProgramId(String tenantId, Long studentId, Long programId);
}


