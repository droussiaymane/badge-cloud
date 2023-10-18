package com.vizzibl.controller;


import com.vizzibl.dto.AddProgramDTO;
import com.vizzibl.dto.AssignProgramDTO;
import com.vizzibl.dto.DeleteStudentProgramDTO;
import com.vizzibl.dto.EnOrDisCollectionDTO;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.program.ProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addProgram(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestBody AddProgramDTO addProgramDTO) {
        return ResponseEntity.ok(programService.addProgram(tenantId, userId, addProgramDTO));
    }

    @PutMapping("/{programId}")
    public ResponseEntity<ResponseObject> updateProgram(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable String programId, @RequestBody AddProgramDTO addProgramDTO) {
        return ResponseEntity.ok(programService.updateProgram(tenantId, userId, programId, addProgramDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteProgram(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {
        return ResponseEntity.ok(programService.deleteProgram(tenantId, id));
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getProgramsList(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(programService.getProgramsList(tenantId, page, limit));
    }

    @GetMapping("/searchByProgramName")
    public ResponseEntity<ResponseObject> searchByProgramName(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam String programName) {

        return ResponseEntity.ok(programService.searchByProgramName(tenantId, programName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {
        return ResponseEntity.ok(programService.findById(tenantId, id));
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<ResponseObject> findProgramByStudentId(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long studentId) {
        return ResponseEntity.ok(programService.findProgramByStudentId(tenantId, studentId));
    }

    @PostMapping("/students/assignProgram")
    public ResponseEntity<ResponseObject> assignProgram(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestBody AssignProgramDTO assign) {
        return ResponseEntity.ok(programService.assignProgram(tenantId, userId, assign));
    }

    @GetMapping("/badge/{id}")
    public ResponseEntity<ResponseObject> getBadgeByProgramId(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable(value = "id") Long programId) {
        return ResponseEntity.ok(programService.getBadgeByProgramId(tenantId, userId, programId));
    }

    @GetMapping("/badge/students/{id}")
    public ResponseEntity<ResponseObject> getBadgesInfoByStudent(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable("id") Long studentId) {
        return ResponseEntity.ok(programService.getBadgesByStudent(tenantId, userId, studentId));
    }

    @PostMapping("/enableOrDisableCollection")
    public ResponseEntity<ResponseObject> enableCollection(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestBody EnOrDisCollectionDTO enOrDisCollectionDTO) {
        return ResponseEntity.ok(programService.enableOrDisableCollection(tenantId, enOrDisCollectionDTO));
    }

    @PostMapping("/deleteStudentFromProgram")
    public ResponseEntity<ResponseObject> deleteStudentFromProgram(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestBody DeleteStudentProgramDTO deleteStudentProgramDTO) {
        return ResponseEntity.ok(programService.deleteStudentFromProgram(tenantId, deleteStudentProgramDTO));
    }

    @GetMapping("/programStudents/{programId}")
    public ResponseEntity<ResponseObject> getStudentListByProgramId(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable(value = "programId") Long programId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit) {

        return ResponseEntity.ok(programService.getStudentListByProgramId(tenantId, programId, page, limit));
    }

    @GetMapping("/count")
    public ResponseEntity<ResponseObject> getTotalNumberOfPrograms() {
        return ResponseEntity.ok(programService.getTotalNumberOfPrograms());
    }

    @PostMapping("/students/assignProgram/bulk/{tenantId}")
    public ResponseEntity<ResponseObject> assignProgramBulk(@PathVariable String tenantId, @RequestParam MultipartFile file) throws Exception {
        return ResponseEntity.ok(programService.assignProgramBulk(tenantId, file));
    }

    @PostMapping("/enableOrDisableCollection/bulk/{tenantId}")
    public ResponseEntity<ResponseObject> enableOrDisableBulk(@PathVariable String tenantId, @RequestParam MultipartFile file) throws Exception {
        return ResponseEntity.ok(programService.enableOrDisableBulk(tenantId, file));
    }

    @PostMapping("/bulk/{tenantId}")
    public ResponseEntity<ResponseObject> bulkProgram(@PathVariable String tenantId, @RequestParam MultipartFile file) throws Exception {
        return ResponseEntity.ok(programService.bulkProgram(tenantId, file));
    }

}
