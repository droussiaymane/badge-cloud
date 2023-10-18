package com.vizzibl.service.evidence;


import com.vizzibl.common.ConstantUtil;
import com.vizzibl.common.FileUtils;
import com.vizzibl.dto.AddEvidenceDTO;
import com.vizzibl.dto.EvidenceDTO;
import com.vizzibl.entity.Evidence;
import com.vizzibl.repository.EvidenceRepository;
import com.vizzibl.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class EvidenceServiceImpl implements EvidenceService {

    @Value("${evidence.files.path}")
    private String evidencePath;

    @Value("${evidence.files.path.upload}")
    private String evidenceUploadPath;

    private final EvidenceRepository evidenceRepository;

    public EvidenceServiceImpl(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    @Override
    public ResponseObject getEvidenceList(String tenant, Integer page, Integer limit) {

        ResponseObject res;

        try {

            Page<Evidence> evidences = evidenceRepository.findAllByTenantId(tenant, PageRequest.of(page, limit));
            res = preparePaginationDto(evidences, tenant, page, limit);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject searchByName(String tenant, String name) {

        ResponseObject res;

        try {
            List<Evidence> evidences = evidenceRepository.findAllByTenantIdAndName(tenant, name);

            if (evidences.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            } else {
                List<EvidenceDTO> evidenceDTOS = evidences.stream().map(entity -> {
                    EvidenceDTO evidenceDTO = new EvidenceDTO();
                    BeanUtils.copyProperties(entity, evidenceDTO);
                    if (entity.getEvidence1() != null) {
                        evidenceDTO.setFile1(evidencePath + "/" + tenant + "/" + entity.getEvidence1());
                    }
                    if (entity.getEvidence2() != null) {
                        evidenceDTO.setFile2(evidencePath + "/" + tenant + "/" + entity.getEvidence2());
                    }
                    if (entity.getEvidence3() != null) {
                        evidenceDTO.setFile3(evidencePath + "/" + tenant + "/" + entity.getEvidence3());
                    }
                    return evidenceDTO;
                }).toList();
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", evidenceDTOS);
                return res;
            }
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject getUnmappedEntries(String tenant) {

        ResponseObject res;

        try {

            List<Evidence> evidences = evidenceRepository.findAllUnmappedEntriesByTenant(tenant);

            res = prepareDto(evidences, tenant);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }


    private ResponseObject preparePaginationDto(Page<Evidence> evidences, String tenantId, Integer page, Integer limit) {

        ResponseObject res;
        List<EvidenceDTO> evidenceList = new ArrayList<>();
        if (evidences.isEmpty()) {
            return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
        } else {
            evidences.getContent().forEach(entity -> {
                EvidenceDTO evidenceDTO = new EvidenceDTO();
                BeanUtils.copyProperties(entity, evidenceDTO);
                if (entity.getEvidence1() != null) {
                    evidenceDTO.setFile1(evidencePath + "/" + tenantId + "/" + entity.getEvidence1());
                }
                if (entity.getEvidence2() != null) {
                    evidenceDTO.setFile2(evidencePath + "/" + tenantId + "/" + entity.getEvidence2());
                }
                if (entity.getEvidence3() != null) {
                    evidenceDTO.setFile3(evidencePath + "/" + tenantId + "/" + entity.getEvidence3());
                }
                evidenceList.add(evidenceDTO);
            });

            Page<EvidenceDTO> evidenceDTO = new PageImpl<>(evidenceList, PageRequest.of(page, limit), evidences.getTotalElements());
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, evidenceDTO);
        }

        return res;
    }

    private ResponseObject prepareDto(List<Evidence> evidences, String tenantId) {

        ResponseObject res;
        List<EvidenceDTO> evidenceList = new ArrayList<>();

        if (evidences.isEmpty()) {
            return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
        } else {
            for (Evidence entity : evidences) {

                EvidenceDTO evidenceDTO = new EvidenceDTO();
                BeanUtils.copyProperties(entity, evidenceDTO);
                if (entity.getEvidence1() != null) {
                    evidenceDTO.setFile1(evidencePath + "/" + tenantId + "/" + entity.getEvidence1());
                }
                if (entity.getEvidence2() != null) {
                    evidenceDTO.setFile2(evidencePath + "/" + tenantId + "/" + entity.getEvidence2());
                }
                if (entity.getEvidence3() != null) {
                    evidenceDTO.setFile3(evidencePath + "/" + tenantId + "/" + entity.getEvidence3());
                }
                evidenceList.add(evidenceDTO);
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, evidenceList);
        }

        return res;
    }

    @Override
    public ResponseObject addOrUpdateEvidence(String tenant, String userId, AddEvidenceDTO addEvidenceDTO) {

        System.out.println("tenant : " + tenant);
        ResponseObject res;
        Evidence evidence;
        if (addEvidenceDTO.getId() != null) {
            Optional<Evidence> evidenceOptional = evidenceRepository.findByIdAndTenantId(addEvidenceDTO.getId(), tenant);
            evidence = evidenceOptional.orElseGet(() ->
            {
                Evidence evidence1 = new Evidence();
                evidence1.setTenantId(tenant);
                evidence1.setCreatedBy(userId);
                return evidence1;
            });
        } else {
            evidence = new Evidence();
            evidence.setTenantId(tenant);
            evidence.setCreatedBy(userId);
        }
        try {

            if (addEvidenceDTO.getName() != null && !addEvidenceDTO.getName().isEmpty()) {
                evidence.setName(addEvidenceDTO.getName());
            }
            if (addEvidenceDTO.getLink() != null && !addEvidenceDTO.getLink().isEmpty()) {
                evidence.setLink(addEvidenceDTO.getLink());
            }
            if (addEvidenceDTO.getDescription() != null && !addEvidenceDTO.getDescription().isEmpty()) {
                evidence.setDescription(addEvidenceDTO.getDescription());
            }

            String uuid = UUID.randomUUID().toString();
            if (addEvidenceDTO.getFile1() != null && !addEvidenceDTO.getFile1().isEmpty()) {
                String fileName1 = FileUtils.renameFile(Objects.requireNonNull(addEvidenceDTO.getFile1().getOriginalFilename()), "evidence-" + 1, uuid);
                FileUtils.createFile(evidenceUploadPath + "/" + tenant, fileName1, addEvidenceDTO.getFile1().getInputStream());
                evidence.setEvidence1(fileName1);
            }
            if (addEvidenceDTO.getFile2() != null && !addEvidenceDTO.getFile2().isEmpty()) {
                String fileName2 = FileUtils.renameFile(Objects.requireNonNull(addEvidenceDTO.getFile2().getOriginalFilename()), "evidence-" + 2, uuid);
                FileUtils.createFile(evidenceUploadPath + "/" + tenant, fileName2, addEvidenceDTO.getFile2().getInputStream());
                evidence.setEvidence2(fileName2);
            }
            if (addEvidenceDTO.getFile3() != null && !addEvidenceDTO.getFile3().isEmpty()) {
                String fileName3 = FileUtils.renameFile(Objects.requireNonNull(addEvidenceDTO.getFile3().getOriginalFilename()), "evidence-" + 3, uuid);
                FileUtils.createFile(evidenceUploadPath + "/" + tenant, fileName3, addEvidenceDTO.getFile3().getInputStream());
                evidence.setEvidence3(fileName3);
            }
            evidenceRepository.save(evidence);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Evidence Added Successfully!!");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject deleteEvidence(String tenant, Long id) {

        ResponseObject res;
        try {
            evidenceRepository.deleteById(id);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Evidence Deleted Successfully!!");

        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject findById(String tenant, Long id) {

        ResponseObject res;

        try {
            Optional<Evidence> optEvidence = evidenceRepository.findById(id);
            if (optEvidence.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "Evidence not Found!!!");
            } else {

                Evidence evidence = optEvidence.get();
                EvidenceDTO dto = new EvidenceDTO();
                BeanUtils.copyProperties(evidence, dto);

                if (evidence.getEvidence1() != null) {
                    dto.setFile1(evidencePath + "/" + tenant + "/" + evidence.getEvidence1());
                }
                if (evidence.getEvidence2() != null) {
                    dto.setFile2(evidencePath + "/" + tenant + "/" + evidence.getEvidence2());
                }
                if (evidence.getEvidence3() != null) {
                    dto.setFile3(evidencePath + "/" + tenant + "/" + evidence.getEvidence3());
                }
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dto);
            }
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }
}
