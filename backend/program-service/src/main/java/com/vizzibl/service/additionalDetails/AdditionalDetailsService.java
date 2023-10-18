package com.vizzibl.service.additionalDetails;


import com.vizzibl.dto.AdditionalDetailsDTO;
import com.vizzibl.response.ResponseObject;

import java.io.IOException;

public interface AdditionalDetailsService {

    ResponseObject getAdditionalDetailsList(String tenant, Integer page, Integer limit);

    ResponseObject searchByName(String tenant, String name);

    ResponseObject findUnmappedEntries(String tenant);

    ResponseObject findById(String tenant, Long id);

    ResponseObject addOrUpdateAdditionalDetails(String tenant, String userId, AdditionalDetailsDTO additionalDetailsDTO) throws IOException;

    ResponseObject deleteAdditionalDetails(String tenant, Long id);
}
