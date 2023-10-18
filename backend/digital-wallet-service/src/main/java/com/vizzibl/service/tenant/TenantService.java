package com.vizzibl.service.tenant;

import com.vizzibl.dto.AddTenantDTO;
import com.vizzibl.response.ResponseObject;

public interface TenantService {

    public ResponseObject addTenant(AddTenantDTO addTenantDTO);

    public ResponseObject getTenant(String tenantId);
}
