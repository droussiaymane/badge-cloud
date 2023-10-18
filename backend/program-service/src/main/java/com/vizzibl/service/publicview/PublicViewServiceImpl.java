package com.vizzibl.service.publicview;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vizzibl.common.ConstantUtil;
import com.vizzibl.dto.*;
import com.vizzibl.entity.EmbeddedQRModel;
import com.vizzibl.entity.InstructorProgram;
import com.vizzibl.entity.ProgramsModel;
import com.vizzibl.entity.StudentProgram;
import com.vizzibl.proxy.DigitalWalletServiceProxy;
import com.vizzibl.repository.EmbeddedQRRepository;
import com.vizzibl.repository.ProgramRepository;
import com.vizzibl.response.ResponseObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PublicViewServiceImpl implements PublicViewService {

    private final EmbeddedQRRepository embeddedQRRepository;
    private final ProgramRepository programRepository;
    private final DigitalWalletServiceProxy digitalWalletServiceProxy;

    @Value("${public.share.url}")
    private String publicShareUrl;

    @Value("${badges.image.path}")
    private String badgeImagesPath;

    public PublicViewServiceImpl(EmbeddedQRRepository embeddedQRRepository, ProgramRepository programRepository, DigitalWalletServiceProxy digitalWalletServiceProxy) {
        this.embeddedQRRepository = embeddedQRRepository;
        this.programRepository = programRepository;
        this.digitalWalletServiceProxy = digitalWalletServiceProxy;
    }


    @Override
    public ResponseObject getUserBadgeViewUrl(String tenantId, String headerUserId, UserBadgeViewUrlDTO userBadgeViewUrlDTO) {
        ResponseObject res;
        try {
            Optional<EmbeddedQRModel> embeddedQRModel = embeddedQRRepository.findByTenantIdAndProgramIdAndStudentId(tenantId, userBadgeViewUrlDTO.getProgramId(), userBadgeViewUrlDTO.getStudentId());

            if (embeddedQRModel.isPresent()) {
                userBadgeViewUrlDTO.setUserBadgeViewUrl(publicShareUrl + embeddedQRModel.get().getPublicViewId());
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", userBadgeViewUrlDTO);
                return res;
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "not found");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred, Please try again later or contact your Administrator : " + e);
        }
        return res;
    }

    @Override
    public void addUserPublicView(String tenantId, UserBadgeViewUrlDTO userBadgeViewUrlDTO) {

        Optional<EmbeddedQRModel> embeddedQRModel = embeddedQRRepository.findByTenantIdAndProgramIdAndStudentId(tenantId, userBadgeViewUrlDTO.getProgramId(), userBadgeViewUrlDTO.getStudentId());
        if (embeddedQRModel.isPresent()) {
            return;
        }
        EmbeddedQRModel qrModel = new EmbeddedQRModel();
        System.out.println(userBadgeViewUrlDTO);
        BeanUtils.copyProperties(userBadgeViewUrlDTO, qrModel);
        qrModel.setPublicViewId(UUID.randomUUID().toString());
        embeddedQRRepository.save(qrModel);
    }

    @Override
    public ResponseObject getUserBadgeView(String publicViewId) {

        ResponseObject res;
        UserBadgeViewDTO userBadgeViewDTO;
        try {
            Optional<EmbeddedQRModel> embeddedQRModel = embeddedQRRepository.findByPublicViewId(publicViewId);

            if (embeddedQRModel.isEmpty()) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "Not found");
                return res;
            }

            EmbeddedQRModel qrModel = embeddedQRModel.get();
            if (qrModel.getProgramId() != null &&
                    qrModel.getStudentId() != null) {
                userBadgeViewDTO = new UserBadgeViewDTO();

                userBadgeViewDTO.setPublicViewId(qrModel.getPublicViewId());

                Optional<ProgramsModel> programsModel = programRepository.findByIdAndTenantId(qrModel.getProgramId(), qrModel.getTenantId());
                if (programsModel.isPresent()) {

                    List<StudentProgram> studentPrograms = programsModel.get().getStudentProgram().stream().filter(x -> x.getStudentId().equals(Long.valueOf(qrModel.getStudentId()))).toList();
//                    boolean badgeMatch = programsModel.get().getProgramBadge().getBadgesModel().getId().equals(Long.valueOf(qrModel.getBadgeId()));

                    if (!studentPrograms.isEmpty()) {
                        BeanUtils.copyProperties(programsModel.get(), userBadgeViewDTO);

                        BadgeDTO badgeDTO = new BadgeDTO();
                        BeanUtils.copyProperties(programsModel.get().getProgramBadge().getBadgesModel(), badgeDTO);
                        badgeDTO.setImage(badgeImagesPath + "/" + qrModel.getTenantId() + "/" + programsModel.get().getProgramBadge().getBadgesModel().getFileName());
                        userBadgeViewDTO.setBadgeDTO(badgeDTO);


                        List<CompetencyDTO> competencyDTOS = new ArrayList<>();
                        programsModel.get().getProgramCompetencies().forEach(
                                x -> {
                                    CompetencyDTO competencyDTO = new CompetencyDTO();
                                    BeanUtils.copyProperties(x.getCompetency(), competencyDTO);
                                    competencyDTOS.add(competencyDTO);
                                }
                        );
                        userBadgeViewDTO.setCompetencyDTOS(competencyDTOS);

                        EvidenceDTO evidenceDTO = new EvidenceDTO();
                        BeanUtils.copyProperties(programsModel.get().getEvidence(), evidenceDTO);
                        userBadgeViewDTO.setEvidenceDTO(evidenceDTO);

                        StandardDTO standardDTO = new StandardDTO();
                        BeanUtils.copyProperties(programsModel.get().getStandard(), standardDTO);
                        userBadgeViewDTO.setStandardDTO(standardDTO);

                        EarningRequirementDTO earningRequirementDTO = new EarningRequirementDTO();
                        BeanUtils.copyProperties(programsModel.get().getEarningRequirement(), earningRequirementDTO);
                        userBadgeViewDTO.setEarningRequirementDTO(earningRequirementDTO);

                        AdditionalDetailsDTO additionalDetailsDTO = new AdditionalDetailsDTO();
                        BeanUtils.copyProperties(programsModel.get().getEarningRequirement(), additionalDetailsDTO);
                        userBadgeViewDTO.setAdditionalDetailsDTO(additionalDetailsDTO);


                        ResponseEntity<ResponseObject> studentByUserId = digitalWalletServiceProxy.findByUserId(qrModel.getTenantId(), null, studentPrograms.get(0).getStudentId().toString());
                        StudentDTO studentDTO = new StudentDTO();
                        if (studentByUserId.getBody() != null && studentByUserId.getBody().getCode() == 1) {
                            UserDTO userDTO = new ObjectMapper().convertValue(
                                    studentByUserId.getBody().getData(),
                                    new TypeReference<>() {
                                    });
                            studentDTO.setName(userDTO.getFirstName() + " " + userDTO.getLastName());
                            studentDTO.setId(userDTO.getId());
                            studentDTO.setPhoto(userDTO.getPhoto());
                        }
                        userBadgeViewDTO.setStudentDTO(studentDTO);

                        ResponseEntity<ResponseObject> instructorByUserIds = digitalWalletServiceProxy.getUserByUserIds(qrModel.getTenantId(), null, programsModel.get().getInstructorProgram().stream().map(InstructorProgram::getInstructorId).map(Object::toString).toList());

                        if (instructorByUserIds.getBody() != null && instructorByUserIds.getBody().getCode() == 1) {
                            List<UserDTO> userDTOs = new ObjectMapper().convertValue(
                                    instructorByUserIds.getBody().getData(),
                                    new TypeReference<>() {
                                    });
                            List<InstructorDTO> instructorDTOS = userDTOs.stream().map(userDTO ->
                            {
                                String fullName = userDTO.getFirstName() + " " + userDTO.getLastName();
                                InstructorDTO instructorDTO = new InstructorDTO();
                                instructorDTO.setName(fullName);
                                instructorDTO.setTenantId(userDTO.getTenantId());
                                instructorDTO.setId(userDTO.getId());
                                return instructorDTO;
                            }).toList();
                            userBadgeViewDTO.setInstructorDTOS(instructorDTOS);
                        }


                        res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", userBadgeViewDTO);

                    } else {
                        res = new ResponseObject(ConstantUtil.ERROR_CODE, "Not found", userBadgeViewDTO);
                    }
                    return res;
                }

            }
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Not found");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "internal server error");
        }
        return res;

    }

}
