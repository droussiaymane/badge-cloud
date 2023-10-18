package com.vizzibl.controller;

import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.dashboard.DashboardStatisticService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dashboard")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardStatisticController {

    final DashboardStatisticService dashboardStatisticService;


    @GetMapping
    public ResponseEntity<ResponseObject> getDashboardStatistics() {
        return dashboardStatisticService.getDashboardStatistics();
    }


}
