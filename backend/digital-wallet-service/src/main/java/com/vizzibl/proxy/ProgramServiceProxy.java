package com.vizzibl.proxy;

import com.vizzibl.config.FeignClientInterceptor;
import com.vizzibl.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "program-service", configuration = {FeignClientInterceptor.class})
public interface ProgramServiceProxy {

    @GetMapping("/programs/students/{studentId}")
    ResponseEntity<ResponseObject> getStudentPrograms(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long studentId);

    @GetMapping("/programs/badge/students/{id}")
    ResponseEntity<ResponseObject> getStudentAndBadgesInfo(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable("id") Long studentId);

    @GetMapping("/programs/count")
    ResponseEntity<ResponseObject> getTotalNumberOfPrograms();
}
