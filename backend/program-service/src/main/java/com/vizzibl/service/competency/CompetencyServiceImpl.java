package com.vizzibl.service.competency;


import com.vizzibl.common.ConstantUtil;
import com.vizzibl.dto.AddCompetencyDTO;
import com.vizzibl.dto.CompetencyDTO;
import com.vizzibl.entity.Competency;
import com.vizzibl.repository.CompetenciesRepository;
import com.vizzibl.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CompetencyServiceImpl implements CompetencyService {
    private final CompetenciesRepository competenciesRepository;

    public CompetencyServiceImpl(CompetenciesRepository competenciesRepository) {
        this.competenciesRepository = competenciesRepository;
    }

    @Override
    public ResponseObject getCompetencyList(String tenantId, Integer page, Integer limit) {
        ResponseObject res;
        try {
            Page<Competency> competencies = competenciesRepository.findAllByTenantId(tenantId, PageRequest.of(page, limit));
            if (competencies.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", competencies);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error please try again later or Contact Administrator!!!" + e);
        }
        return res;
    }

    @Override
    public ResponseObject searchByCompetencyName(String tenant, String competencyName) {
        ResponseObject res;
        try {
            List<Competency> competencies = competenciesRepository.findByTenantIdAndName(tenant, competencyName);
            if (competencies.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", competencies);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error please try again later or Contact Administrator!!!");
        }
        return res;
    }

    @Override
    public ResponseObject getUnmappedEntries(String tenant) {
        List<CompetencyDTO> dtos = new ArrayList<>();
        ResponseObject res;
        try {
            List<Competency> competencies = competenciesRepository.findAllUnmappedEntriesByTenantId(tenant);
            for (Competency competency : competencies) {
                CompetencyDTO dto = new CompetencyDTO();
                BeanUtils.copyProperties(competency, dto);
                dtos.add(dto);
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dtos);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error please try again later or Contact Administrator!!!");
        }
        return res;

    }

    @Override
    public ResponseObject addOrUpdateCompetency(String tenant, String userId, AddCompetencyDTO competencyDTO) {

        ResponseObject res;
        Competency competency = new Competency();
        try {
            BeanUtils.copyProperties(competencyDTO, competency);
            competency.setTenantId(tenant);
            competency.setCreatedById(userId);
            competenciesRepository.save(competency);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Competency Successfully Added!!!");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please try gain late or Contact your Administrator!!!");
        }
        return res;
    }

    @Override
    public ResponseObject deleteCompetency(String tenant, Long id) {
        ResponseObject res;
        try {
            competenciesRepository.deleteById(id);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Competency Deleted Successfully!!!");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please try gain late or Contact your Administrator!!!");
        }
        return res;
    }

    @Override
    public ResponseObject findById(String tenant, Long id) {

        ResponseObject res;
        CompetencyDTO dto = new CompetencyDTO();
        try {
            Optional<Competency> optCompetency = competenciesRepository.findById(id);
            if (optCompetency.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "Competency Not Found!!!");
            }
            Competency competency = optCompetency.get();
            BeanUtils.copyProperties(competency, dto);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dto);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please try gain late or Contact your Administrator!!!");
        }
        return res;
    }
}
