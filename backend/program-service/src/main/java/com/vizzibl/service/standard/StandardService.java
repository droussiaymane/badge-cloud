package com.vizzibl.service.standard;


import com.vizzibl.dto.AddStandardDTO;
import com.vizzibl.response.ResponseObject;

public interface StandardService {

    ResponseObject addOrUpdateStandard(String tenant, String userId, AddStandardDTO standardDTO);

    ResponseObject getStandardList(String tenant, int page, int limit);

    ResponseObject searchByName(String tenant, String name);

    ResponseObject getUnmappedEntries(String tenant);

    ResponseObject deleteStandard(String tenant, Long standardId);

    ResponseObject findById(String tenant, Long id);
}
