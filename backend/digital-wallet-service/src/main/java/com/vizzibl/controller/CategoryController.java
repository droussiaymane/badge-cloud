package com.vizzibl.controller;

import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.category.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> findByUsername(@RequestHeader(required = false) String tenantId) {
        return ResponseEntity.ok(categoryService.getAllByTenantId(tenantId));
    }
}
