package com.vizzibl.service.earningRequirement;


import com.vizzibl.dto.EarningRequirementDTO;
import com.vizzibl.response.ResponseObject;

public interface EarningRequirementService {

    ResponseObject getEarningRequirementList(String tenant, Integer page, Integer limit);

    ResponseObject searchByName(String tenant, String name);

    ResponseObject addOrUpdateEarningRequirement(String tenant, String userId, EarningRequirementDTO earningRequirementDTO);

    ResponseObject deleteEarningRequirement(String tenant, Long id);

    ResponseObject findById(String tenant, Long id);

    ResponseObject getUnmappedEntries(String tenant);
}
