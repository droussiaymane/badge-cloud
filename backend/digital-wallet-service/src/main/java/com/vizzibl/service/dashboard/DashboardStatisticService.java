package com.vizzibl.service.dashboard;

import com.vizzibl.response.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface DashboardStatisticService {
    ResponseEntity<ResponseObject> getDashboardStatistics();
}
