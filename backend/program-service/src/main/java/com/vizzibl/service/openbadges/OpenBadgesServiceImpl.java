package com.vizzibl.service.openbadges;

import com.vizzibl.common.ConstantUtil;
import com.vizzibl.common.FileUtils;
import com.vizzibl.config.BadgeCloudConfig;
import com.vizzibl.controller.velocity.VelocityBadgesService;
import com.vizzibl.dto.ExpireRevokeOpenBadgeDTO;
import com.vizzibl.dto.StudentOpenBadgeViewDTO;
import com.vizzibl.entity.*;
import com.vizzibl.entity.assertion.AssertionBakingDTO;
import com.vizzibl.repository.*;
import com.vizzibl.response.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OpenBadgesServiceImpl implements OpenBadgesService {

    private final ExpireRevokeStatusRepository revokeStatusRepository;
    private final BadgeBakingService badgeBakingService;

    private final StudentProgramRepository studentProgramRepository;

    private final OpenBadgeMappingRepository openBadgeMappingRepository;

    private final AssertionModelRepository assertionModelRepository;
    private final ProgramRepository programRepository;
    private final HttpServletRequest request;
    private final BadgeCloudConfig config;
    private final Environment environment;

    private final VelocityBadgesService velocityBadgesService;

    private final RestTemplate restTemplate;

    @Value("${badges.image.path}")
    private String badgeImagesPath;

    @Value("${badges.image.path.upload}")
    private String badgeImagesUploadPath;

    public OpenBadgesServiceImpl(ExpireRevokeStatusRepository revokeStatusRepository, BadgeBakingService badgeBakingService, StudentProgramRepository studentProgramRepository, OpenBadgeMappingRepository openBadgeMappingRepository, AssertionModelRepository assertionModelRepository, ProgramRepository programRepository, HttpServletRequest request, BadgeCloudConfig config, Environment environment, VelocityBadgesService velocityBadgesService, RestTemplate restTemplate) {
        this.revokeStatusRepository = revokeStatusRepository;
        this.badgeBakingService = badgeBakingService;
        this.studentProgramRepository = studentProgramRepository;
        this.openBadgeMappingRepository = openBadgeMappingRepository;
        this.assertionModelRepository = assertionModelRepository;
        this.programRepository = programRepository;
        this.request = request;
        this.config = config;
        this.environment = environment;
        this.velocityBadgesService = velocityBadgesService;
        this.restTemplate = restTemplate;
    }


    @Override
    public ResponseObject updateExpirationRevokeOpenBadge(String tenantId, ExpireRevokeOpenBadgeDTO expireRevokeOpenBadgeDTO) {

        ResponseObject res;

        try {

            Optional<ExpireRevokeStatus> revokeStatus = revokeStatusRepository.findByTenantIdAndStudentIdAndProgramId(tenantId,
                    expireRevokeOpenBadgeDTO.getStudentId(), expireRevokeOpenBadgeDTO.getProgramId()
            );

            if (revokeStatus.isPresent()) {
                ExpireRevokeStatus revokeStatusUpdate = revokeStatus.get();
                if (expireRevokeOpenBadgeDTO.getRevokeStatus() != null) {
                    revokeStatusUpdate.setRevokeStatus(expireRevokeOpenBadgeDTO.getRevokeStatus());
                }
                if (expireRevokeOpenBadgeDTO.getRevokeReason() != null) {
                    revokeStatusUpdate.setRevokeReason(expireRevokeOpenBadgeDTO.getRevokeReason());
                }
                if (expireRevokeOpenBadgeDTO.getExpiresStatus() != null &&
                        expireRevokeOpenBadgeDTO.getExpiresStatus() &&
                        expireRevokeOpenBadgeDTO.getExpirationDate() != null &&
                        !expireRevokeOpenBadgeDTO.getExpirationDate().isEmpty()) {

                    String expirationDate = expireRevokeOpenBadgeDTO.getExpirationDate();
                    Calendar cal = Calendar.getInstance();
                    String[] dateArray = expirationDate.split("/");
                    cal.set(Calendar.DATE, Integer.parseInt(dateArray[1]));
                    cal.set(Calendar.MONTH, Integer.parseInt(dateArray[0]) - 1);
                    cal.set(Calendar.YEAR, Integer.parseInt(dateArray[2]));
                    // Change date format to DD-MM-YYYY
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDateTime date = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE)).atStartOfDay();
                    revokeStatusUpdate.setExpires(date);
                    revokeStatusUpdate.setExpiresStatus(expireRevokeOpenBadgeDTO.getExpiresStatus());


                } else if (expireRevokeOpenBadgeDTO.getExpiresStatus() != null &&
                        expireRevokeOpenBadgeDTO.getExpiresStatus()) {
                    revokeStatusUpdate.setExpires(LocalDateTime.now(ZoneOffset.UTC).plusYears(1));
                    revokeStatusUpdate.setExpiresStatus(expireRevokeOpenBadgeDTO.getExpiresStatus());

                } else {
                    revokeStatusUpdate.setExpires(null);
                    revokeStatusUpdate.setExpiresStatus(false);
                }
                revokeStatusRepository.save(revokeStatusUpdate);

                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "SUCCESS", revokeStatusUpdate);
                return res;
            }
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "invalid student or program");

        } catch (Exception exception) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "internal server error :" + exception);
        }
        return res;
    }


    @Override
    public void enableOpenBadge(String tenantId, Long studentId, Long programId, Long badgeId, String fileName, StudentProgram studentProgram) {

        System.out.println(" tenantId = " + tenantId + " : studentId = " + studentId + " :  programId = " + programId + " :  badgeId = " + badgeId + " :  fileName = " + fileName);

        Optional<OpenBadgeMappingModel> opMapping = openBadgeMappingRepository.findByTenantIdAndProgramIdAndStudentId(tenantId, programId, studentId);

        if (opMapping.isEmpty()) {
            Optional<ExpireRevokeStatus> revokeStatusOptional =
                    revokeStatusRepository.findByTenantIdAndStudentIdAndProgramId(tenantId, studentId, programId);

            if (revokeStatusOptional.isEmpty()) {
                ExpireRevokeStatus expireRevokeStatus = new ExpireRevokeStatus();
                expireRevokeStatus.setTenantId(tenantId);
                expireRevokeStatus.setRevokeStatus(false);
                expireRevokeStatus.setStudentId(studentId);
                expireRevokeStatus.setProgramId(programId);
                expireRevokeStatus.setExpires(null);
                expireRevokeStatus.setExpiresStatus(false);
                revokeStatusRepository.save(expireRevokeStatus);
            }

            String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
            OpenBadgeMappingModel openBadgeMappingModel = new OpenBadgeMappingModel();
            openBadgeMappingModel.setTenantId(tenantId);
            openBadgeMappingModel.setProgramId(programId);
            openBadgeMappingModel.setBadgeId(badgeId);
            openBadgeMappingModel.setStudentId(studentId);
            openBadgeMappingModel.setFileName(badgeImagesUploadPath + "/" + tenantId + "/" + fileName);

            //String offerId = velocityBadgesService.createOffer(tenantId, studentProgram);
            //if (offerId != null && !offerId.isEmpty()) {
            //    openBadgeMappingModel.setIframe(offerId);
            //}
            openBadgeMappingRepository.save(openBadgeMappingModel);

            AssertionModel assertionModel = new AssertionModel();
            assertionModel.setIssuedOn(LocalDateTime.now(ZoneOffset.UTC));
            assertionModel.setAssertionKey(UUID.randomUUID().toString());
            assertionModel.setOpenBadgeMappingModel(openBadgeMappingModel);
            assertionModelRepository.save(assertionModel);
        }
    }


    @Override
    public ResponseObject studentOpenBadgeView(String tenantId, Long studentId, Long programId) {

        System.out.println("tenantId = " + tenantId + " : studentId = " + studentId + " :  programId = " + programId);
        ResponseObject res;

        try {

            Optional<OpenBadgeMappingModel> opMapping = openBadgeMappingRepository.findByTenantIdAndProgramIdAndStudentId(tenantId, programId, studentId);

            StudentOpenBadgeViewDTO studentOpenBadgeViewDTO = new StudentOpenBadgeViewDTO();
            if (opMapping.isPresent()) {
                Optional<ProgramsModel> program = programRepository.findByIdAndTenantId(programId, tenantId);
                if (program.isPresent()) {
                    BeanUtils.copyProperties(opMapping.get(), studentOpenBadgeViewDTO);

                    Optional<ExpireRevokeStatus> revokeStatusOptional =
                            revokeStatusRepository.findByTenantIdAndStudentIdAndProgramId(tenantId, studentId, programId);
                    if (revokeStatusOptional.isPresent()) {
                        BeanUtils.copyProperties(revokeStatusOptional.get(), studentOpenBadgeViewDTO);
                        studentOpenBadgeViewDTO.setExpirationStatus(revokeStatusOptional.get().isExpiresStatus());
                        studentOpenBadgeViewDTO.setRevokedStatus(revokeStatusOptional.get().isRevokeStatus());
                        studentOpenBadgeViewDTO.setRevokedReason(revokeStatusOptional.get().getRevokeReason());
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-uuuu");
                        if (revokeStatusOptional.get().getExpires() != null) {
                            studentOpenBadgeViewDTO.setExpirationDate(revokeStatusOptional.get().getExpires().atOffset(ZoneOffset.UTC).format(dtf));

                        }
                        studentOpenBadgeViewDTO.setIssuerName(config.getIssuerName());
                        studentOpenBadgeViewDTO.setIssuedOn(opMapping.get().getAssertionModel().getIssuedOn().atOffset(ZoneOffset.UTC).format(dtf));
                        studentOpenBadgeViewDTO.setDescription(program.get().getDescription());
                        studentOpenBadgeViewDTO.setName(program.get().getProgramName());
                        studentOpenBadgeViewDTO.setPhoto(badgeImagesPath + "/" + tenantId + "/" + program.get().getProgramBadge().getBadgesModel().getFileName());

                    }
                }
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "SUCCESS", studentOpenBadgeViewDTO);
                return res;
            }

            res = new ResponseObject(ConstantUtil.ERROR_CODE, "invalid student or program", studentOpenBadgeViewDTO);
        } catch (Exception exception) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "internal server error :" + exception);
        }
        return res;

    }


    @Override
    public ResponseEntity<Resource> createCertificatedBadgeV2(String tenantId, Long studentId, Long programId) throws IOException {
        System.out.println("tenantId = " + tenantId + " : studentId = " + studentId + " :  programId = " + programId);

        Optional<OpenBadgeMappingModel> opMapping = openBadgeMappingRepository.findByTenantIdAndProgramIdAndStudentId(tenantId, programId, studentId);

        if (opMapping.isPresent()) {
            OpenBadgeMappingModel mapping = opMapping.get();

            String uploadPath = mapping.getFileName();
            String[] split = uploadPath.split("/");
            String newFileName = tenantId + "_" + studentId + "_" + split[split.length - 1];
            String tempSavePath = environment.getProperty("image-save-path-upload");

            tempSavePath = FileUtils.copyFileUsingStream(new File(uploadPath), newFileName, tempSavePath);

            AssertionBakingDTO assertionDto = badgeBakingService.createAssertion(mapping);

            Map<String, Object> map = new HashMap<>();
            map.put("imgUrl", uploadPath);
            map.put("saveUrl", tempSavePath);
            map.put("jsonFile", assertionDto);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

            ResponseEntity<Object> response = restTemplate.postForEntity(environment.getProperty("metadata-service-url") + "/add-tags-to-image", entity, Object.class);

            final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(tempSavePath)));

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentLength(inputStream.contentLength())
                    .contentType(MediaType.parseMediaType(Files.probeContentType(Paths.get(tempSavePath))))
                    .body(inputStream);
        }

        // Handle case where OpenBadgeMappingModel is not present
        return ResponseEntity.notFound().build();
    }

}