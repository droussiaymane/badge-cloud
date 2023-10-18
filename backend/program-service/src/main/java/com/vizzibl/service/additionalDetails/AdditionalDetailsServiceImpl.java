package com.vizzibl.service.additionalDetails;


import com.vizzibl.common.ConstantUtil;
import com.vizzibl.dto.AdditionalDetailsDTO;
import com.vizzibl.entity.AdditionalDetails;
import com.vizzibl.repository.AdditionalDetailsRepository;
import com.vizzibl.response.ResponseObject;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdditionalDetailsServiceImpl implements AdditionalDetailsService {

    private final AdditionalDetailsRepository additionalDetailsRepository;

    public AdditionalDetailsServiceImpl(AdditionalDetailsRepository additionalDetailsRepository) {
        this.additionalDetailsRepository = additionalDetailsRepository;
    }


    @Override
    public ResponseObject getAdditionalDetailsList(String tenant, Integer page, Integer limit) {

        ResponseObject res;
        try {
            Page<AdditionalDetails> additionalDetails = additionalDetailsRepository.findAllByTenantId(tenant, PageRequest.of(page, limit));
            if (additionalDetails.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "success", additionalDetails);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject searchByName(String tenant, String name) {

        ResponseObject res;
        try {
            List<AdditionalDetails> additionalDetails = additionalDetailsRepository.findAllByTenantIdAndName(tenant, name);
            if (additionalDetails.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "success", additionalDetails);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject findUnmappedEntries(String tenant) {

        ResponseObject res;
        try {
            List<AdditionalDetails> additionalDetails = additionalDetailsRepository.getAllUnmappedDetailsByTenantId(tenant);

            res = prepareDto(additionalDetails);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }

    private ResponseObject prepareDto(List<AdditionalDetails> additionalDetails) {

        List<AdditionalDetailsDTO> additionalDetailsDTOS = new ArrayList<>();
        ResponseObject res;
        try {

            if (additionalDetails.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }
            additionalDetails.forEach(adt -> {
                AdditionalDetailsDTO dto = new AdditionalDetailsDTO();
                BeanUtils.copyProperties(adt, dto);
                additionalDetailsDTOS.add(dto);
            });
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", additionalDetailsDTOS);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject findById(String tenant, Long id) {

        AdditionalDetailsDTO dto = new AdditionalDetailsDTO();
        ResponseObject res;
        try {
            Optional<AdditionalDetails> optionalAdditionalDetails = additionalDetailsRepository.findById(id);
            if (optionalAdditionalDetails.isEmpty()) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "Additional Details not Found!!!");
            } else {
                AdditionalDetails additionalDetails = optionalAdditionalDetails.get();
                BeanUtils.copyProperties(additionalDetails, dto);

                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dto);
            }
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject addOrUpdateAdditionalDetails(String tenant, String userId, AdditionalDetailsDTO additionalDetailsDTO) {

        ResponseObject res;
        AdditionalDetails additionalDetails = new AdditionalDetails();
        try {

            BeanUtils.copyProperties(additionalDetailsDTO, additionalDetails);
            additionalDetails.setTenantId(tenant);
            additionalDetails.setCreatedBy(userId);
            additionalDetailsRepository.save(additionalDetails);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Additional Details added Successfully!!!");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject deleteAdditionalDetails(String tenant, Long id) {

        ResponseObject res;
        try {
            additionalDetailsRepository.deleteById(id);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Additional Details Deleted Successfully!!!");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }
}
