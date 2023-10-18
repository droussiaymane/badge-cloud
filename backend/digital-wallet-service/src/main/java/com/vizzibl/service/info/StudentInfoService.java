package com.vizzibl.service.info;

import com.vizzibl.response.ResponseObject;

public interface StudentInfoService {

    ResponseObject getStudentAndBadgesInfo(String tenantId, String userId, Long studentId);
}
