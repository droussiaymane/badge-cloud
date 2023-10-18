package com.vizzibl.service.user;

import com.vizzibl.dto.AddUserDTO;
import com.vizzibl.dto.UpdateUserProfileDTO;
import com.vizzibl.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    ResponseObject addUser(String tenant, String userId, AddUserDTO dto);

    ResponseObject updateUser(String tenant, String headerUserId, String userId, AddUserDTO dto);

    ResponseObject updateUserProfile(String tenant, String headerUserId, String userId, UpdateUserProfileDTO dto);

    ResponseObject getUserProfile(String tenant, String headerUserId, String userId);

    ResponseObject getUserProfileByKid(String tenant, String headerUserId, String sub);

    //
    ResponseObject findByUserId(String tenant, String id);


    ResponseObject getUserByUserIds(String tenant, List<Long> userIds);

//    ResponseObject findByUserName(String tenant, String userName);

    public ResponseObject findByCategoryAndUserName(String tenant, String category, String userName);

    ResponseObject deleteById(String tenant, String id);

    ResponseObject findStudentsByProgramId(String tenant, Long programId);

    ResponseObject findUsersByTenantId(String tenant, Integer page, Integer limit);

    ResponseObject getStudentPrograms(String tenantId, String userId, Long studentId);

    void bulkCreateUsers(String tenantId, MultipartFile file) throws Exception;

    ResponseObject findByCategoryAndEmails(String category, List<String> emails);

//    ResponseObject findById(Long id, String tenantId);

    ResponseEntity<Void> triggerUserRegistration();

    ResponseObject getUserProfileDetailsByKid(String tenant, String headerUserId, String sub);
}
