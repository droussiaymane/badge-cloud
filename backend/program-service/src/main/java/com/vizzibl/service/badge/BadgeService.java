package com.vizzibl.service.badge;


import com.vizzibl.dto.AddBadgeDTO;
import com.vizzibl.response.ResponseObject;

import java.io.IOException;

public interface BadgeService {

    ResponseObject addOrUpdateBadge(String tenant, String userId, AddBadgeDTO badgeDTO) throws IOException;

    ResponseObject getBadgesList(String tenant, Integer page, Integer limit);

    ResponseObject getBadgesByBadgeName(String tenant, String BadgeName);

    ResponseObject deleteBadge(String tenant, Long id);

    ResponseObject findById(String tenant, Long id);

    ResponseObject getUnmappedEntries(String tenant);
}
