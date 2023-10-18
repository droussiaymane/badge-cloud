package com.vizzibl.controller;


import com.vizzibl.dto.AddCompetencyDTO;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.competency.CompetencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/competencies")
public class CompetencyController {
    private final CompetencyService competencyService;

    public CompetencyController(CompetencyService competencyService) {
        this.competencyService = competencyService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getCompetencyList(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(competencyService.getCompetencyList(tenantId, page, limit));
    }


    @GetMapping("/searchByCompetencyName")
    public ResponseEntity<ResponseObject> searchByCompetencyName(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam String competencyName) {
        return ResponseEntity.ok(competencyService.searchByCompetencyName(tenantId, competencyName));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addOrUpdateCompetency(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestBody AddCompetencyDTO competencyDTO) {
        return ResponseEntity.ok(competencyService.addOrUpdateCompetency(tenantId, userId, competencyDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCompetency(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {
        return ResponseEntity.ok(competencyService.deleteCompetency(tenantId, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {
        return ResponseEntity.ok(competencyService.findById(tenantId, id));
    }

    @GetMapping("/assign")
    public ResponseEntity<ResponseObject> findUnmappedEntries(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId) {
        return ResponseEntity.ok(competencyService.getUnmappedEntries(tenantId));
    }
}
