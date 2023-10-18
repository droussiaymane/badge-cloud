package com.vizzibl.service.competency;


import com.vizzibl.dto.AddCompetencyDTO;
import com.vizzibl.response.ResponseObject;

public interface CompetencyService {

    ResponseObject getCompetencyList(String tenant, Integer page, Integer limit);

    ResponseObject searchByCompetencyName(String tenant, String competencyName);

    ResponseObject getUnmappedEntries(String tenant);

    ResponseObject addOrUpdateCompetency(String tenant, String userId, AddCompetencyDTO competencyDTO);

    ResponseObject deleteCompetency(String tenant, Long id);

    ResponseObject findById(String tenant, Long id);
}
