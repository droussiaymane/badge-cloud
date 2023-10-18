package com.vizzibl.service.category;

import com.vizzibl.response.ResponseObject;

public interface CategoryService {
    ResponseObject getAllByTenantId(String tenantId);
}
