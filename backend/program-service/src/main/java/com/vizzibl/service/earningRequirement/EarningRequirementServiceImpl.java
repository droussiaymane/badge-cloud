package com.vizzibl.service.earningRequirement;


import com.vizzibl.common.ConstantUtil;
import com.vizzibl.dto.EarningRequirementDTO;
import com.vizzibl.entity.EarningRequirement;
import com.vizzibl.repository.EarningRequirementRepository;
import com.vizzibl.response.ResponseObject;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EarningRequirementServiceImpl implements EarningRequirementService {

    private final EarningRequirementRepository requirementRepository;

    public EarningRequirementServiceImpl(EarningRequirementRepository requirementRepository) {
        this.requirementRepository = requirementRepository;
    }

    @Override
    public ResponseObject getEarningRequirementList(String tenant, Integer page, Integer limit) {
        ResponseObject res;
        try {
            Page<EarningRequirement> requirements = requirementRepository.findAllByTenantId(tenant, PageRequest.of(page, limit));
            if (requirements.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", requirements);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred, Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject searchByName(String tenant, String name) {
        ResponseObject res;
        try {
            List<EarningRequirement> requirements = requirementRepository.findAllByTenantIdAndName(tenant, name);
            if (requirements.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", requirements);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred, Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject addOrUpdateEarningRequirement(String tenant, String userId, EarningRequirementDTO earningRequirementDTO) {

        ResponseObject res;
        try {
            EarningRequirement req = new EarningRequirement();
            BeanUtils.copyProperties(earningRequirementDTO, req);
            req.setTenantId(tenant);
            req.setCreatedBy(userId);
            requirementRepository.save(req);

            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Earning Requirements Successfully Added!!!");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred, Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject deleteEarningRequirement(String tenant, Long id) {

        ResponseObject res;
        try {
            requirementRepository.deleteById(id);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Earning Requirement Deleted Successfully!!!");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred, Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject findById(String tenant, Long id) {

        ResponseObject res;
        EarningRequirementDTO requirementDTO = new EarningRequirementDTO();
        try {

            Optional<EarningRequirement> optionalEarningRequirement = requirementRepository.findById(id);
            if (optionalEarningRequirement.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "Earning Requirement not Found!!!");
            }

            EarningRequirement req = optionalEarningRequirement.get();
            BeanUtils.copyProperties(req, requirementDTO);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", requirementDTO);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred, Please try again later or contact your Administrator");
        }

        return res;
    }

    @Override
    public ResponseObject getUnmappedEntries(String tenant) {
        ResponseObject res;
        List<EarningRequirementDTO> dtoList;
        try {
            List<EarningRequirement> requirements = requirementRepository.findAllUnmappedEntriesByTenantId(tenant);
            if (requirements.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }

            dtoList = requirements.stream().map(req -> {
                EarningRequirementDTO dto = new EarningRequirementDTO();
                BeanUtils.copyProperties(req, dto);
                return dto;
            }).collect(Collectors.toList());

            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dtoList);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred, Please try again later or contact your Administrator");
        }
        return res;
    }
}
