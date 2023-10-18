package com.vizzibl.service.standard;


import com.vizzibl.common.ConstantUtil;
import com.vizzibl.dto.AddStandardDTO;
import com.vizzibl.dto.StandardDTO;
import com.vizzibl.entity.Standard;
import com.vizzibl.repository.StandardRepository;
import com.vizzibl.response.ResponseObject;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StandardServiceImpl implements StandardService {

    private final StandardRepository standardRepository;

    public StandardServiceImpl(StandardRepository standardRepository) {
        this.standardRepository = standardRepository;
    }

    @Override
    public ResponseObject addOrUpdateStandard(String tenantId, String userId, AddStandardDTO standardDTO) {

        ResponseObject res;
        try {
            Standard standard = new Standard();
            BeanUtils.copyProperties(standardDTO, standard);
            standard.setTenantId(tenantId);
            standard.setCreatedById(userId);
            standardRepository.save(standard);

            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Standard Added Successfully!!");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please try gain later or Contact your Administrator!!!" + e);
        }
        return res;
    }

    @Override
    public ResponseObject getStandardList(String tenant, int page, int limit) {

        ResponseObject res;
        try {

            Page<Standard> standards = standardRepository.findAllByTenantId(tenant, PageRequest.of(page, limit));
            if (standards.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success!!", standards);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please try gain late or Contact your Administrator!!!");
        }
        return res;
    }

    @Override
    public ResponseObject searchByName(String tenant, String name) {
        ResponseObject res;
        try {

            List<Standard> standards = standardRepository.findByTenantIdAndName(tenant, name);
            if (standards.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success!!", standards);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please try gain late or Contact your Administrator!!!");

        }
        return res;
    }

    @Override
    public ResponseObject getUnmappedEntries(String tenant) {

        ResponseObject res;
        List<StandardDTO> standardsList = new ArrayList<>();
        try {

            List<Standard> standards = standardRepository.findUnmappedByTenantId(tenant);
            standards.forEach(standard -> {
                StandardDTO standardDto = new StandardDTO();
                BeanUtils.copyProperties(standard, standardDto);
                standardsList.add(standardDto);
            });

            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success!!", standardsList);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please try gain late or Contact your Administrator!!!");

        }
        return res;
    }

    @Override
    public ResponseObject deleteStandard(String tenant, Long standardId) {

        ResponseObject res;
        try {
            standardRepository.deleteById(standardId);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Standard Deleted Successfully!!");

        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please try gain late or Contact your Administrator!!!");
        }

        return res;
    }

    @Override
    public ResponseObject findById(String tenant, Long id) {

        ResponseObject res;
        try {

            Optional<Standard> optionalStandard = standardRepository.findById(id);
            if (optionalStandard.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "Standard Not found!!!");
            }
            Standard standard = optionalStandard.get();
            StandardDTO dto = new StandardDTO();

            BeanUtils.copyProperties(standard, dto);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dto);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please try gain late or Contact your Administrator!!!");
        }
        return res;
    }

}
