package com.vizzibl.service.evidence;


import com.vizzibl.dto.AddEvidenceDTO;
import com.vizzibl.response.ResponseObject;

import java.io.IOException;


public interface EvidenceService {

    ResponseObject getEvidenceList(String tenant, Integer page, Integer limit);

    ResponseObject searchByName(String tenant, String name);

    ResponseObject getUnmappedEntries(String tenant);

    ResponseObject addOrUpdateEvidence(String tenant, String userId, AddEvidenceDTO addEvidenceDTO) throws IOException;

    ResponseObject deleteEvidence(String tenant, Long id);

    ResponseObject findById(String tenant, Long id);
}
