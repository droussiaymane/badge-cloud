package com.vizzibl.service.openbadges;

import com.vizzibl.dto.ExpireRevokeOpenBadgeDTO;
import com.vizzibl.entity.StudentProgram;
import com.vizzibl.response.ResponseObject;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface OpenBadgesService {


    ResponseObject updateExpirationRevokeOpenBadge(String tenantId, ExpireRevokeOpenBadgeDTO expireRevokeOpenBadgeDTO);

    ResponseObject studentOpenBadgeView(String tenantId, Long studentId, Long programId);

    void enableOpenBadge(String tenantId, Long studentId, Long programId, Long badgeId, String photos, StudentProgram studentProgram);

    ResponseEntity<Resource> createCertificatedBadgeV2(String tenantId, Long studentId, Long programId) throws IOException;
}
