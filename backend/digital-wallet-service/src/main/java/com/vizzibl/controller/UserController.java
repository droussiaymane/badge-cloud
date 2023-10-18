package com.vizzibl.controller;


import com.vizzibl.dto.AddUserDTO;
import com.vizzibl.dto.UpdateUserProfileDTO;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.info.StudentInfoService;
import com.vizzibl.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final StudentInfoService infoService;

    public UserController(UserService userService, StudentInfoService infoService) {
        this.userService = userService;
        this.infoService = infoService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addUser(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @ModelAttribute AddUserDTO dto) {
        return ResponseEntity.ok(userService.addUser(tenantId, userId, dto));
    }

    @PutMapping("{userId}")
    public ResponseEntity<ResponseObject> updateUser(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable String userId, @ModelAttribute AddUserDTO dto) {
        return ResponseEntity.ok(userService.updateUser(tenantId, headerUserId, userId, dto));
    }

    @PostMapping("/updateUserProfile/{userId}")
    public ResponseEntity<ResponseObject> updateUserProfile(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable String userId, @ModelAttribute UpdateUserProfileDTO dto) {
        return ResponseEntity.ok(userService.updateUserProfile(tenantId, headerUserId, userId, dto));
    }

    @GetMapping("/userProfile/{userId}")
    public ResponseEntity<ResponseObject> getUserProfile(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserProfile(tenantId, headerUserId, userId));
    }

    @GetMapping("/getKUser/{sub}")
    public ResponseEntity<ResponseObject> getUserProfileByKid(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable String sub) {
        return ResponseEntity.ok(userService.getUserProfileByKid(tenantId, headerUserId, sub));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getUserList(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit) {

        return ResponseEntity.ok(userService.findUsersByTenantId(tenantId, page, limit));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseObject> deleteById(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable String userId) {

        return ResponseEntity.ok(userService.deleteById(tenantId, userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseObject> findByUserId(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable String userId) {
        return ResponseEntity.ok(userService.findByUserId(tenantId, userId));
    }


    @GetMapping("/byIds")
    public ResponseEntity<ResponseObject> getUserByUserIds(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @RequestParam List<Long> userIds) {
        return ResponseEntity.ok(userService.getUserByUserIds(tenantId, userIds));
    }


    @GetMapping("/searchByName")
    public ResponseEntity<ResponseObject> findByUsername(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @RequestParam String category, @RequestParam String userName) {
        return ResponseEntity.ok(userService.findByCategoryAndUserName(tenantId, category, userName));
    }

    @GetMapping("students/{programId}")
    public ResponseEntity<ResponseObject> findByProgramId(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable Long programId) {

        return ResponseEntity.ok(userService.findStudentsByProgramId(tenantId, programId));
    }

    @GetMapping("students/programs/{studentId}")
    public ResponseEntity<ResponseObject> getStudentPrograms(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable Long studentId) {
        return ResponseEntity.ok(userService.getStudentPrograms(tenantId, headerUserId, studentId));
    }

    @GetMapping("students/home/{studentId}")
    public ResponseEntity<ResponseObject> getStudentHomeData(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable Long studentId) {
        return ResponseEntity.ok(infoService.getStudentAndBadgesInfo(tenantId, headerUserId, studentId));
    }

    @PostMapping("/bulk/{tenantId}")
    public String createBulkUser(@PathVariable String tenantId, @RequestParam MultipartFile file) throws Exception {
        userService.bulkCreateUsers(tenantId, file);
        return "Bulk users creation is done!";
    }

    @GetMapping("/searchByEmail")
    public ResponseEntity<ResponseObject> findByEmails(@RequestParam String category, @RequestParam List<String> emails) {
        return ResponseEntity.ok(userService.findByCategoryAndEmails(category, emails));
    }

    @PostMapping("/trigger/registration")
    public ResponseEntity<Void> triggerUserRegistration() {
        return userService.triggerUserRegistration();
    }

    @GetMapping("/getKUser/details/{sub}")
    public ResponseEntity<ResponseObject> getUserProfileDetailsByKid(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable String sub) {
        return ResponseEntity.ok(userService.getUserProfileDetailsByKid(tenantId, headerUserId, sub));
    }
}
