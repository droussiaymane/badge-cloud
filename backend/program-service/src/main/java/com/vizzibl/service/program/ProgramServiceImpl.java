package com.vizzibl.service.program;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.vizzibl.common.ConstantUtil;
import com.vizzibl.dto.*;
import com.vizzibl.entity.*;
import com.vizzibl.proxy.DigitalWalletServiceProxy;
import com.vizzibl.proxy.EmailServiceProxy;
import com.vizzibl.repository.*;
import com.vizzibl.request.OfferRequest;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.openbadges.OpenBadgesService;
import com.vizzibl.service.publicview.PublicViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private RestTemplate restTemplate;
    private final ProgramRepository programRepository;

    private final InstructorProgramRepository instructorProgramRepository;

    private final StudentProgramRepository studentProgramRepository;

    private final OpenBadgeMappingRepository openBadgeMappingRepository;

    private final ExpireRevokeStatusRepository expireRevokeStatusRepository;

    private final ProgramStatisticRepository programStatisticRepository;

    private final ProgramStatisticInstructorRepository programStatisticInstructorRepository;

    private final ProgramBadgeRepository programBadgeRepository;

    private final CompetenciesRepository competenciesRepository;

    private final EarningRequirementRepository earningRequirementRepository;

    private final StandardRepository standardRepository;

    private final ProgramAdditionalDetailsRepository additionalDetailsRepository;

    private final ProgramCompetencyRepository programCompetencyRepository;

    private final EvidenceRepository evidenceRepository;

    private final DigitalWalletServiceProxy digitalWalletServiceProxy;

    private final BadgeRepository badgeRepository;

    private final PublicViewService publicViewService;

    private final OpenBadgesService openBadgesService;

    private final ObjectMapper objectMapper;

    private final EmailServiceProxy emailService;

    private final HttpServletRequest request;


    @Value("${badges.image.path}")
    private String badgeImagesPath;

    public ProgramServiceImpl(ProgramRepository programRepository, InstructorProgramRepository instructorProgramRepository, StudentProgramRepository studentProgramRepository, OpenBadgeMappingRepository openBadgeMappingRepository, ExpireRevokeStatusRepository expireRevokeStatusRepository, ProgramStatisticRepository programStatisticRepository, ProgramStatisticInstructorRepository programStatisticInstructorRepository, ProgramBadgeRepository programBadgeRepository, CompetenciesRepository competenciesRepository, EarningRequirementRepository earningRequirementRepository, StandardRepository standardRepository, ProgramAdditionalDetailsRepository additionalDetailsRepository, ProgramCompetencyRepository programCompetencyRepository, EvidenceRepository evidenceRepository, DigitalWalletServiceProxy digitalWalletServiceProxy, BadgeRepository badgeRepository, PublicViewService publicViewService, OpenBadgesService openBadgesService, ObjectMapper objectMapper, EmailServiceProxy emailService, HttpServletRequest request) {
        this.programRepository = programRepository;
        this.instructorProgramRepository = instructorProgramRepository;
        this.studentProgramRepository = studentProgramRepository;
        this.openBadgeMappingRepository = openBadgeMappingRepository;
        this.expireRevokeStatusRepository = expireRevokeStatusRepository;
        this.programStatisticRepository = programStatisticRepository;
        this.programStatisticInstructorRepository = programStatisticInstructorRepository;
        this.programBadgeRepository = programBadgeRepository;
        this.competenciesRepository = competenciesRepository;
        this.earningRequirementRepository = earningRequirementRepository;
        this.standardRepository = standardRepository;
        this.additionalDetailsRepository = additionalDetailsRepository;
        this.programCompetencyRepository = programCompetencyRepository;
        this.evidenceRepository = evidenceRepository;
        this.digitalWalletServiceProxy = digitalWalletServiceProxy;
        this.badgeRepository = badgeRepository;
        this.publicViewService = publicViewService;
        this.openBadgesService = openBadgesService;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
        this.request = request;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseObject addProgram(String tenantId, String userId, AddProgramDTO programDto) {

        ResponseObject res;
        try {
            ProgramsModel programsModel = new ProgramsModel();

            if (tenantId != null) {
                programsModel.setTenantId(tenantId);
                if (userId != null)
                    programsModel.setCreatedById(userId);
            }
            programsModel.setProgramName(programDto.getProgramName());
            programsModel.setDescription(programDto.getDescription());
            programsModel.setIssuedBy(programDto.getIssuedBy());
            programsModel.setEndDate(programDto.getEndDate());
            programsModel.setStartDate(programDto.getStartDate());

            List<Evidence> evidence = evidenceRepository.findAllByTenantIdAndName(tenantId, "");
            if (evidence != null && evidence.size() > 0) {
                programsModel.setEvidence(evidence.get(0));
            } else {
                Evidence ev = new Evidence();
                ev.setName("");
                evidenceRepository.save(ev);
                programsModel.setEvidence(ev);
            }


            List<AdditionalDetails> additionalDetails = additionalDetailsRepository.findAllByTenantIdAndName(tenantId, "");
            if (additionalDetails != null && additionalDetails.size() > 0) {
                programsModel.setAdditionalDetails(additionalDetails.get(0));
            } else {
                AdditionalDetails ad = new AdditionalDetails();
                ad.setName("");
                additionalDetailsRepository.save(ad);
                programsModel.setAdditionalDetails(ad);
            }


            List<Standard> standard = standardRepository.findByTenantIdAndName(tenantId, "");
            if (standard != null && standard.size() > 0) {
                programsModel.setStandard(standard.get(0));
            } else {
                Standard stan = new Standard();
                stan.setName("");
                standardRepository.save(stan);
                programsModel.setStandard(stan);
            }


            List<EarningRequirement> earningRequirement = earningRequirementRepository.findAllByTenantIdAndName(tenantId, "");
            if (earningRequirement != null && earningRequirement.size() > 0) {
                programsModel.setEarningRequirement(earningRequirement.get(0));
            } else {
                EarningRequirement ear = new EarningRequirement();
                ear.setName("");
                earningRequirementRepository.save(ear);
                programsModel.setEarningRequirement(ear);
            }

            programsModel = programRepository.save(programsModel);


            if (programDto.getBadgeId() != null) {
                ProgramBadge programBadge = new ProgramBadge();
                Optional<BadgesModel> badgesModel = badgeRepository.findById(programDto.getBadgeId());
                if (badgesModel.isPresent()) {
                    programBadge.setBadgesModel(badgesModel.get());
                    programBadge.setProgramsModel(programsModel);
                    programBadgeRepository.save(programBadge);
                } else {
                    res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid Badge");
                    return res;
                }
            } else {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid Badge");
                return res;
            }

            programRepository.save(programsModel);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Program Added Successfully!!!");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Internal server error :" + e.getMessage());
        }

        return res;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseObject updateProgram(String tenantId, String userId, String programId, AddProgramDTO programDto) {

        ResponseObject res;
        try {

            Optional<ProgramsModel> programsModel1 = programRepository.findById(Long.parseLong(programId));
            if (programsModel1.isPresent()) {
                ProgramsModel programsModel = programsModel1.get();
                if (tenantId != null) {
                    programsModel.setTenantId(tenantId);
                    if (userId != null)
                        programsModel.setCreatedById(userId);
                }
                if (programDto.getProgramName() != null && !programDto.getProgramName().isEmpty()) {
                    programsModel.setProgramName(programDto.getProgramName());
                }
                if (programDto.getDescription() != null && !programDto.getDescription().isEmpty()) {
                    programsModel.setDescription(programDto.getDescription());
                }
                if (programDto.getIssuedBy() != null && !programDto.getIssuedBy().isEmpty()) {
                    programsModel.setIssuedBy(programDto.getIssuedBy());
                }
                if (programDto.getEndDate() != null && !programDto.getEndDate().isEmpty()) {
                    programsModel.setEndDate(programDto.getEndDate());
                }
                if (programDto.getStartDate() != null && !programDto.getStartDate().isEmpty()) {
                    programsModel.setStartDate(programDto.getStartDate());
                }

                if (programDto.getEvidence() != null && programDto.getEvidence() > 0) {
                    Optional<Evidence> evidence = evidenceRepository.findByIdAndTenantId(programDto.getEvidence(), tenantId);
                    if (evidence.isEmpty()) {
                        res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid Evidence");
                        return res;
                    }

                    programsModel.setEvidence(evidence.get());
                }
                if (programDto.getAdditionalDetails() != null && programDto.getAdditionalDetails() > 0) {
                    Optional<AdditionalDetails> additionalDetails = additionalDetailsRepository.findByIdAndTenantId(programDto.getAdditionalDetails(), tenantId);
                    if (additionalDetails.isEmpty()) {
                        res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid Additional Details");
                        return res;
                    }
                    programsModel.setAdditionalDetails(additionalDetails.get());
                }
                if (programDto.getStandardId() != null && programDto.getStandardId() > 0) {
                    Optional<Standard> standard = standardRepository.findByIdAndTenantId(programDto.getStandardId(), tenantId);
                    if (standard.isEmpty()) {
                        res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid Standard");
                        return res;
                    }
                    programsModel.setStandard(standard.get());
                }
                if (programDto.getEarningRequirementId() != null && programDto.getEarningRequirementId() > 0) {
                    Optional<EarningRequirement> earningRequirement = earningRequirementRepository.findByIdAndTenantId(programDto.getEarningRequirementId(), tenantId);
                    if (earningRequirement.isEmpty()) {
                        res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid Earning Requirement");
                        return res;
                    }
                    programsModel.setEarningRequirement(earningRequirement.get());
                    programsModel = programRepository.save(programsModel);
                }
                if (programDto.getCompetencies() != null && !programDto.getCompetencies().isEmpty()) {
                    List<Competency> competencies = competenciesRepository.findAllByIdIn(programDto.getCompetencies());
                    if (competencies.isEmpty()) {
                        res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid or Empty Competency");
                        return res;
                    } else {
                        for (Competency competency : competencies) {
                            Optional<ProgramCompetency> programCompetency = programCompetencyRepository.findByCompetency_IdAndProgramsModel_Id(competency.getId(), programsModel.getId());
                            if (programCompetency.isEmpty()) {
                                ProgramCompetency pc = new ProgramCompetency(competency, programsModel);
                                programCompetencyRepository.save(pc);
                            }

                        }
                    }

                }

                if (programDto.getBadgeId() != null && programDto.getBadgeId() > 0) {
                    ProgramBadge programBadge;
                    Optional<BadgesModel> badgesModel = badgeRepository.findById(programDto.getBadgeId());
                    if (badgesModel.isPresent()) {
                        if (badgesModel.get().getProgramBadge() != null) {
                            programBadge = badgesModel.get().getProgramBadge();
                            programBadge.setProgramsModel(programsModel);
                        } else {
                            programBadge = new ProgramBadge();
                            programBadge.setBadgesModel(badgesModel.get());
                            programBadge.setProgramsModel(programsModel);
                        }
                        programBadgeRepository.save(programBadge);
                    } else {
                        res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid Badge");
                        return res;
                    }
                } else {
                    res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid Badge");
                    return res;
                }

                if (programDto.getInstructorsId() != null && !programDto.getInstructorsId().isEmpty()) {
                    List<InstructorProgram> instructors = new ArrayList<>();
                    for (Long InstructorsId : programDto.getInstructorsId()) {
                        InstructorProgram instructorProgram = new InstructorProgram();
                        instructorProgram.setProgram(programsModel);
                        instructorProgram.setInstructorId(InstructorsId);
                        instructorProgramRepository.save(instructorProgram);
                        instructors.add(instructorProgram);
                    }
                    programsModel.setInstructorProgram(instructors);

                } else {
                    res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid Instructors");
                    return res;
                }

                if (programDto.getStudentsId() != null && !programDto.getStudentsId().isEmpty()) {
                    List<StudentProgram> studentsProgram = new ArrayList<>();
                    for (Long stId : programDto.getStudentsId()) {
                        StudentProgram studentProgram = new StudentProgram();
                        studentProgram.setProgram(programsModel);
                        studentProgram.setStudentId(stId);
                        studentProgram.setStatus(0);
                        studentProgramRepository.save(studentProgram);
                        studentsProgram.add(studentProgram);
                    }
                    programsModel.setStudentProgram(studentsProgram);
                }

                programRepository.save(programsModel);
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Program Added Successfully!!!");
                return res;
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Program not found");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Internal server error :" + e.getMessage());
        }
        return res;
    }

    @Override
    @Transactional
    public ResponseObject deleteProgram(String tenant, Long programId) {

        ResponseObject res;
        try {
            programRepository.deleteById(programId);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Program Deleted Successfully!!!");

        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please try again later or contact your Administrator!!!");
        }
        return res;
    }


    @Override
    public ResponseObject getProgramsList(String tenant, Integer page, Integer limit) {

        List<ProgramDTO> programsList = new ArrayList<>();

        ResponseObject res;
        Page<ProgramsModel> programs =
                programRepository.findAllByTenantId(tenant, PageRequest.of(page, limit));
//        try {

        programs.getContent().forEach(program -> {
            ProgramDTO programDto = new ProgramDTO();
            BeanUtils.copyProperties(program, programDto);
            BadgesModel badgesModel = program.getProgramBadge().getBadgesModel();
            BadgeDTO badgeDTO = new BadgeDTO();
            badgeDTO.setBadgeName(badgesModel.getBadgeName());
            badgeDTO.setId(badgeDTO.getId());
            badgeDTO.setImage(badgeImagesPath + "/" + tenant + "/" + badgesModel.getFileName());
            programDto.setBadgeDTO(badgeDTO);

            EvidenceDTO evidenceDTO = new EvidenceDTO();
            BeanUtils.copyProperties(program.getEvidence(), evidenceDTO);
            programDto.setEvidence(evidenceDTO);

            List<Competency> competencies = program.getProgramCompetencies().stream().map(ProgramCompetency::getCompetency).toList();
            if (!competencies.isEmpty()) {
                List<CompetencyDTO> competencyDTOS = competencies.stream().map(c -> {
                    CompetencyDTO dto = new CompetencyDTO();
                    BeanUtils.copyProperties(c, dto);
                    return dto;
                }).toList();
                programDto.setCompetencies(competencyDTOS);
            } else {
                programDto.setCompetencies(null);
            }

            AdditionalDetailsDTO additionalDetailsDTO = new AdditionalDetailsDTO();
            BeanUtils.copyProperties(program.getAdditionalDetails(), additionalDetailsDTO);
            programDto.setAdditionalDetails(additionalDetailsDTO);

            StandardDTO standardDTO = new StandardDTO();
            BeanUtils.copyProperties(program.getStandard(), standardDTO);
            programDto.setStandard(standardDTO);

            EarningRequirementDTO erDto = new EarningRequirementDTO();
            BeanUtils.copyProperties(program.getEarningRequirement(), erDto);
            programDto.setEarningRequirements(erDto);

            ResponseEntity<ResponseObject> instructorByUserIds = digitalWalletServiceProxy.getUserByUserIds(tenant, null, program.getInstructorProgram().stream().map(InstructorProgram::getInstructorId).map(Object::toString).toList());

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
                programDto.setInstructors(instructorDTOS);
            }


            ResponseEntity<ResponseObject> studentByUserIds = digitalWalletServiceProxy.getUserByUserIds(tenant, null, program.getStudentProgram().stream().map(StudentProgram::getStudentId).map(Object::toString).toList());

            if (studentByUserIds.getBody() != null && studentByUserIds.getBody().getCode() == 1) {

                List<UserDTO> userDTOs = new ObjectMapper().convertValue(
                        studentByUserIds.getBody().getData(),
                        new TypeReference<>() {
                        });
                List<StudentDTO> studentDTOS = userDTOs.stream().map(userDTO ->
                {
                    String fullName = userDTO.getFirstName() + " " + userDTO.getLastName();
                    StudentDTO studentDTO = new StudentDTO();
                    studentDTO.setName(fullName);
                    studentDTO.setTenantId(userDTO.getTenantId());
                    studentDTO.setId(userDTO.getId());

                    program.getStudentProgram().stream().filter(x -> x.getStudentId().equals(userDTO.getId()))
                            .forEach(y -> studentDTO.setStatus(y.getStatus()));

                    return studentDTO;
                }).toList();


                programDto.setStudents(studentDTOS);
            }
            programsList.add(programDto);
        });
        Page<ProgramDTO> programDTOS = new PageImpl<>(programsList, PageRequest.of(page, limit), programs.getTotalElements());
        res = new ResponseObject(ConstantUtil.SUCCESS_CODE, programDTOS);
//        } catch (Exception e) {
//            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error please try again later or Contact Administrator!!!"+e);
//        }

        return res;
    }

    @Override
    public ResponseObject searchByProgramName(String tenant, String programName) {
        ResponseObject res;
        List<ProgramsModel> programs =
                programRepository.findAllByTenantIdAndProgramBadge(tenant, programName);
        try {

            List<ProgramSearchDTO> programSearchDTOS = programs.stream().map(p ->
            {
                ProgramSearchDTO dto = new ProgramSearchDTO();
                dto.setProgramId(p.getId());
                dto.setProgramName(p.getProgramName());
                dto.setTenantId(p.getTenantId());
                dto.setEndDate(p.getEndDate());
                dto.setStartDate(p.getStartDate());
                return dto;
            }).toList();

            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, programSearchDTOS);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error please try again later or Contact Administrator!!!");
        }

        return res;
    }

    @Override
    public ResponseObject findById(String tenant, Long id) {

        ResponseObject res = null;

        Optional<ProgramsModel> optionalProgramsModel = programRepository.findById(id);

        if (optionalProgramsModel.isPresent()) {
            try {

                ProgramsModel program = optionalProgramsModel.get();
                ProgramDTO programDTO = new ProgramDTO();
                BeanUtils.copyProperties(program, programDTO);

                AdditionalDetailsDTO additionalDetailsDTO = new AdditionalDetailsDTO();
                AdditionalDetails additionalDetails = program.getAdditionalDetails();
                BeanUtils.copyProperties(additionalDetails, additionalDetailsDTO);
                programDTO.setAdditionalDetails(additionalDetailsDTO);

                Standard standard = program.getStandard();
                StandardDTO standardDTO = new StandardDTO();
                BeanUtils.copyProperties(standard, standardDTO);
                programDTO.setStandard(standardDTO);

                Evidence evidence = program.getEvidence();
                EvidenceDTO evidenceDTO = new EvidenceDTO();
                BeanUtils.copyProperties(evidence, evidenceDTO);
                programDTO.setEvidence(evidenceDTO);

                List<Competency> competencies = program.getProgramCompetencies().stream().map(ProgramCompetency::getCompetency).toList();
                List<CompetencyDTO> competencyList = new ArrayList<>();
                competencies.forEach(c -> {
                    CompetencyDTO competency = new CompetencyDTO();
                    BeanUtils.copyProperties(c, competency);
                    competencyList.add(competency);
                });
                programDTO.setCompetencies(competencyList);
                BadgesModel badgesModel = program.getProgramBadge().getBadgesModel();
                BadgeDTO badgeDTO = new BadgeDTO();
                BeanUtils.copyProperties(badgesModel, badgeDTO);
                programDTO.setBadgeDTO(badgeDTO);

                EarningRequirementDTO erDto = new EarningRequirementDTO();
                BeanUtils.copyProperties(program.getEarningRequirement(), erDto);
                programDTO.setEarningRequirements(erDto);

                ResponseEntity<ResponseObject> instructorByUserIds = digitalWalletServiceProxy.getUserByUserIds(tenant, null, program.getInstructorProgram().stream().map(InstructorProgram::getInstructorId).map(Object::toString).toList());

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
                    programDTO.setInstructors(instructorDTOS);
                }


                ResponseEntity<ResponseObject> studentByUserIds = digitalWalletServiceProxy.getUserByUserIds(tenant, null, program.getStudentProgram().stream().map(StudentProgram::getStudentId).map(Object::toString).toList());


                if (studentByUserIds.getBody() != null && studentByUserIds.getBody().getCode() == 1) {

                    List<UserDTO> userDTOs = new ObjectMapper().convertValue(
                            studentByUserIds.getBody().getData(),
                            new TypeReference<>() {
                            });
                    List<StudentDTO> studentDTOS = userDTOs.stream().map(userDTO ->
                    {
                        String fullName = userDTO.getFirstName() + " " + userDTO.getLastName();
                        StudentDTO studentDTO = new StudentDTO();
                        studentDTO.setName(fullName);
                        studentDTO.setTenantId(userDTO.getTenantId());
                        studentDTO.setId(userDTO.getId());

                        program.getStudentProgram().stream().filter(x -> x.getStudentId().equals(userDTO.getId()))
                                .forEach(y -> studentDTO.setStatus(y.getStatus()));

                        return studentDTO;
                    }).toList();

                    programDTO.setStudents(studentDTOS);
                }

                //need feign call to fetch students and instructors

                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, programDTO);
            } catch (Exception e) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error please try again later or Contact Administrator!!!");
            }
        }

        return res;
    }

    @Override
    public ResponseObject findProgramByStudentId(String tenant, Long studentId) {

        ResponseObject res;
        List<ProgramDTO> currentPrograms = null;
        List<ProgramDTO> availablePrograms;
        StudentProgramDTO studentProgramDTO = new StudentProgramDTO();
        try {

            List<ProgramsModel> programs = programRepository.getAllByTenantIdWithoutPagination(tenant);
            List<ProgramsModel> studentPrograms = programRepository.findAllByStudentId(tenant, studentId);

            if (studentPrograms != null && !studentPrograms.isEmpty()) {

                programs = programs.stream().filter(p -> !studentPrograms.contains(p)).toList();
            }
            availablePrograms = programs.stream().map(p -> {
                ProgramDTO dto = new ProgramDTO();
                BeanUtils.copyProperties(p, dto);
                return dto;
            }).toList();

            if (studentPrograms != null) {
                currentPrograms = studentPrograms.stream().map(p -> {
                    ProgramDTO dto = new ProgramDTO();
                    BeanUtils.copyProperties(p, dto);
                    return dto;
                }).toList();
            }

            studentProgramDTO.setStudentId(studentId);
            studentProgramDTO.setAvailablePrograms(availablePrograms);
            studentProgramDTO.setCurrentPrograms(currentPrograms);

            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", studentProgramDTO);
        } catch (Exception e) {
            e.printStackTrace();

            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error please try again later or Contact Administrator!!!" + e);

        }

        return res;
    }

    @Transactional
    @Override
    public ResponseObject assignProgram(String tenantId, String userId, AssignProgramDTO assign) {

        ResponseObject res;
        Set<Long> currentProgramIds;
        try {
            if (assign == null) {
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "No Change Done!!!");
            } else {

                if (assign.getCurrentProgramIds() == null) {
                    currentProgramIds = new HashSet<>();
                } else {
                    currentProgramIds = assign.getCurrentProgramIds();
                }
                if (assign.getAssignedProgramIds() != null && !assign.getAssignedProgramIds().isEmpty()) {
                    currentProgramIds.addAll(assign.getAssignedProgramIds());
                }

                if (!currentProgramIds.isEmpty()) {

                    studentProgramRepository.deleteByStudentId(assign.getStudentId());
                    for (Long programId : currentProgramIds) {

                        StudentProgram sp = new StudentProgram();
                        sp.setProgram(new ProgramsModel(programId));
                        sp.setStudentId(assign.getStudentId());
                        sp.setStatus(0);
                        studentProgramRepository.save(sp);
                    }
                }

                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject getBadgeByProgramId(String tenantId, String userId, Long programId) {

        ResponseObject res;

        try {
            ProgramsModel p = programRepository.findById(programId).orElse(null);
            if (p == null) {
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "No Badge Assigned to this program");
            } else {
                BadgeDTO dto = new BadgeDTO();
                BeanUtils.copyProperties(p.getProgramBadge().getBadgesModel(), dto);
                dto.setImage(badgeImagesPath + "/" + tenantId + "/" + p.getProgramBadge().getBadgesModel().getFileName());
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dto);
            }
        } catch (Exception e) {

            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject getBadgesByStudent(String tenantId, String userId, Long studentId) {

        ResponseObject res;

        try {
            List<ProgramsModel> programs = programRepository.getAllByStudentId(tenantId, studentId);
            if (programs.isEmpty()) {
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "No Badge earned");
            } else {

                List<ProgramBadgeDTO> dtos = programs.stream().map(p -> {

                    if (p.getProgramBadge() != null) {
                        ProgramBadgeDTO dto = new ProgramBadgeDTO();
                        BeanUtils.copyProperties(p.getProgramBadge().getBadgesModel(), dto);

                        dto.setProgramId(p.getId());
                        dto.setImage(badgeImagesPath + "/" + tenantId + "/" + p.getProgramBadge().getBadgesModel().getFileName());
                        dto.setProgramName(p.getProgramName());
                        return dto;
                    }
                    return new ProgramBadgeDTO();
                }).toList();

                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dtos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error");
        }
        return res;
    }

    @Override
    public ResponseObject enableOrDisableCollection(String tenantId, EnOrDisCollectionDTO dto) {

        ResponseObject res;
        try {
            Optional<StudentProgram> studentProgram = studentProgramRepository.findByProgramIdAndStudentId(dto.getProgramId(), dto.getStudentId());
            if (studentProgram.isPresent()) {
                // set the wallet type on studentProgram
                studentProgram.get().setWallet(dto.getWalletType());

                if (dto.getIsEnableCollection())
                    studentProgram.get().setStatus(1);
                else {
                    studentProgram.get().setStatus(0);
                }
                if (dto.getIsEnableCollection()) {

                    if(!dto.getWalletType().equals("velocity")){
                        UserBadgeViewUrlDTO badgeViewUrlDTO = new UserBadgeViewUrlDTO();
                        BeanUtils.copyProperties(dto, badgeViewUrlDTO);
                        badgeViewUrlDTO.setTenantId(tenantId);
                        publicViewService.addUserPublicView(tenantId, badgeViewUrlDTO);

                        openBadgesService.enableOpenBadge(tenantId, studentProgram.get().getStudentId(),
                                studentProgram.get().getProgram().getId(),
                                studentProgram.get().getProgram().getProgramBadge().getBadgesModel().getId(),
                                studentProgram.get().getProgram().getProgramBadge().getBadgesModel().getFileName(), studentProgram.get());

                        if (dto.getIsEnableCollection() && (dto.getIsBulk() == null || !dto.getIsBulk())) {
                            List<BadgeEmailDetailsDto> emails = new ArrayList<>();
                            ResponseEntity<ResponseObject> studentByUserId = digitalWalletServiceProxy.findByUserId(tenantId, null, dto.getStudentId().toString());
                            UserDTO userDTO = null;
                            if (studentByUserId.getBody() != null && studentByUserId.getBody().getCode() == 1) {
                                userDTO = new ObjectMapper().convertValue(
                                        studentByUserId.getBody().getData(),
                                        new TypeReference<>() {
                                        });

                            }
                            request.getRemoteUser();
                            System.out.println(request.getRemoteUser());
                            ResponseEntity<ResponseObject> issuer = digitalWalletServiceProxy.getUserProfileDetailsByKid(tenantId, null, request.getRemoteUser());
                            UserDTO issuerUser = null;
                            if (issuer.getBody() != null && issuer.getBody().getCode() == 1) {
                                issuerUser = new ObjectMapper().convertValue(
                                        issuer.getBody().getData(),
                                        new TypeReference<>() {
                                        });

                            }
                            BadgeEmailDetailsDto badgeEmail = BadgeEmailDetailsDto.builder().to(userDTO.getEmail()).firstName(userDTO.getFirstName()).lastName(userDTO.getLastName())
                                    .program(studentProgram.get().getProgram().getProgramName())
                                    .issuerName(issuerUser.getFirstName()).createdBy(issuerUser.getId().toString()).build();
                            emails.add(badgeEmail);
                            emailService.scheduleEmailBadge(emails);
                        }
                    }
                    else{
                        String badgeName = studentProgram.get().getProgram().getProgramName();
                       // String badgeImage = "http://38.242.246.183:8080/opt/badgecloud-v2/content/badge/images/a5a8e60d-eed2-4c49-addd-b09e2e42c3d9/" + studentProgram.get().getProgram().getProgramBadge().getBadgesModel().getFileName();
                        String badgeImage = "https://file-server.badgecloud.io/badgecloud-v2/content/badge/images/a5a8e60d-eed2-4c49-addd-b09e2e42c3d9/"+ studentProgram.get().getProgram().getProgramBadge().getBadgesModel().getFileName();
                        Long studentId = studentProgram.get().getStudentId();


                        ResponseEntity<ResponseObject> studentByUserId = digitalWalletServiceProxy.findByUserId(tenantId, null, studentId.toString());
                        UserDTO userDTO = new ObjectMapper().convertValue(
                                studentByUserId.getBody().getData(),
                                new TypeReference<>() {
                                });

                        String userEmail = userDTO.getEmail();
                        String firstName = userDTO.getFirstName();
                        String lastName = userDTO.getLastName();


                        createBadgeInVelocityNetwork(badgeName, badgeImage, userEmail, firstName, lastName);
                    }




                }

                studentProgramRepository.save(studentProgram.get());

                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", studentProgram.get());
                return res;
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Invalid Program or Student Id");

        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error : " + e.getMessage());
        }
        return res;

    }


    public void createBadgeInVelocityNetwork( String badgeName,String badgeImage,String userEmail,String firstName,String lastName){
        String serviceUrl = "http://localhost:8077/api/v1.0/velocity/offer/create";
        OfferRequest offerRequest = new OfferRequest();
        offerRequest.setBadgeName(badgeName);
        offerRequest.setBadgeImage(badgeImage);
        offerRequest.setUserEmail(userEmail);
        offerRequest.setFirstName(firstName);
        offerRequest.setLastName(lastName);

        try {
            // make a POST request
            ResponseEntity<ResponseObject> response = restTemplate.postForEntity(serviceUrl, offerRequest, ResponseObject.class);




        } catch (Exception e) {
            // handle exception
            log.error(e.getMessage());
            e.printStackTrace();
            log.error("qr code not created, something went wrong ....");
        }
    }

    @Override
    public ResponseObject deleteStudentFromProgram(String tenantId, DeleteStudentProgramDTO dto) {

        ResponseObject res;
        try {
            Optional<StudentProgram> studentProgram = studentProgramRepository.findByProgramIdAndStudentId(dto.getProgramId(), dto.getStudentId());
            if (studentProgram.isPresent() && studentProgram.get().getProgram().getTenantId().equals(tenantId)) {
                studentProgramRepository.delete(studentProgram.get());
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success");
                return res;
            }

            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Invalid Program or Student Id");

        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error");
        }
        return res;

    }

    @Override
    public ResponseObject getStudentListByProgramId(String tenantId, Long programId, Integer page, Integer limit) {
        ResponseObject res;

        try {
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<StudentProgram> studentProgramList = studentProgramRepository.findAllByTenantIdAndProgramId(tenantId, programId, pageRequest);

            List<OBStudentProgramListDTO> studentProgramListDTOS = studentProgramList.getContent().stream().map(studentProgram -> {
                OBStudentProgramListDTO dto = new OBStudentProgramListDTO();
                dto.setWallet(studentProgram.getWallet());
                BeanUtils.copyProperties(studentProgram, dto);
                Optional<OpenBadgeMappingModel> openBadgeMapping = openBadgeMappingRepository.findByTenantIdAndProgramIdAndStudentId(tenantId, studentProgram.getProgram().getId(), studentProgram.getStudentId());

                ResponseEntity<ResponseObject> studentByUserId = digitalWalletServiceProxy.findByUserId(tenantId, null, studentProgram.getStudentId().toString());
                if (studentByUserId.getBody() != null && studentByUserId.getBody().getCode() == 1) {
                    UserDTO userDTO = new ObjectMapper().convertValue(
                            studentByUserId.getBody().getData(),
                            new TypeReference<>() {
                            });
                    dto.setStudentName(userDTO.getFirstName() + " " + userDTO.getLastName());
                }

                if (openBadgeMapping.isPresent()) {
                    BeanUtils.copyProperties(openBadgeMapping.get(), dto);
                    Optional<ExpireRevokeStatus> revokeStatus = expireRevokeStatusRepository.findByTenantIdAndStudentIdAndProgramId(tenantId, openBadgeMapping.get().getStudentId(), openBadgeMapping.get().getProgramId());

                    if (revokeStatus.isPresent()) {
                        System.out.println("Test " + revokeStatus.get().getExpires());
                        BeanUtils.copyProperties(revokeStatus.get(), dto);
                        dto.setExpires(revokeStatus.get().getExpires());
                    }
                }
                return dto;
            }).toList();

            Page<OBStudentProgramListDTO> studentPrograms = new PageImpl<>(studentProgramListDTOS, pageRequest, studentProgramListDTOS.size());

            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "SUCCESS", studentPrograms);
        } catch (Exception exception) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "internal server error");
        }
        return res;
    }

    @Override
    public ResponseObject getTotalNumberOfPrograms() {
        long totalNumberOfPrograms = programRepository.count();
        return new ResponseObject(ConstantUtil.SUCCESS_CODE, "SUCCESS", totalNumberOfPrograms);
    }

    @Override
    public ResponseObject assignProgramBulk(String tenantId, MultipartFile file) throws Exception {
        ResponseObject res;
        Reader reader = new InputStreamReader(file.getInputStream());
        try (CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {
            List<String[]> rows = csvReader.readAll();
            List<String> emails = rows.stream().map(x -> x[0]).collect(Collectors.toList());
            ResponseEntity<ResponseObject> students = digitalWalletServiceProxy.findByEmails("Student", emails);

            if (students.getBody() != null && students.getBody().getCode() == 1) {
                List<UserDTO> userDTOs = new ObjectMapper().convertValue(
                        students.getBody().getData(),
                        new TypeReference<>() {
                        });
                for (String[] row : rows) {
                    StudentProgram sp = new StudentProgram();
                    sp.setProgram(programRepository.findByProgramName(row[1]));
                    Optional<UserDTO> user = userDTOs.stream().filter(x -> x.getEmail().equals(row[0])).findFirst();
                    sp.setStudentId(user.get().getId());
                    sp.setStatus(0);
                    studentProgramRepository.save(sp);
                }
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "SUCCESS", null);
        }
        return res;
    }

    @Override
    public ResponseObject enableOrDisableBulk(String tenantId, MultipartFile file) throws Exception {
        {
            ResponseObject res;
            Reader reader = new InputStreamReader(file.getInputStream());
            List<BadgeEmailDetailsDto> emailList = new ArrayList<>();
            try (CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {
                List<String[]> rows = csvReader.readAll();
                List<String> emails = rows.stream().map(x -> x[0]).collect(Collectors.toList());
                ResponseEntity<ResponseObject> students = digitalWalletServiceProxy.findByEmails("Student", emails);
                if (students.getBody() != null && students.getBody().getCode() == 1) {
                    List<UserDTO> userDTOs = new ObjectMapper().convertValue(
                            students.getBody().getData(),
                            new TypeReference<>() {
                            });
                    for (String[] row : rows) {
                        EnOrDisCollectionDTO enOrDisCollectionDTO = new EnOrDisCollectionDTO();
                        ProgramsModel model = programRepository.findByProgramName(row[1]);
                        enOrDisCollectionDTO.setProgramId(model.getId());
                        enOrDisCollectionDTO.setProgramName(model.getProgramName());
                        enOrDisCollectionDTO.setIsEnableCollection(row[2].equalsIgnoreCase("TRUE") ? true : false);
                        enOrDisCollectionDTO.setTenantId(tenantId);
                        Optional<UserDTO> user = userDTOs.stream().filter(x -> x.getEmail().equals(row[0])).findFirst();
                        enOrDisCollectionDTO.setStudentId(user.get().getId());
                        enOrDisCollectionDTO.setIsBulk(Boolean.TRUE);
                        enableOrDisableCollection(tenantId, enOrDisCollectionDTO);
                        ResponseEntity<ResponseObject> studentByUserId = digitalWalletServiceProxy.findByUserId(tenantId, null, user.get().getId().toString());
                        UserDTO userDTO = null;
                        if (studentByUserId.getBody() != null && studentByUserId.getBody().getCode() == 1) {
                            userDTO = new ObjectMapper().convertValue(
                                    studentByUserId.getBody().getData(),
                                    new TypeReference<>() {
                                    });
                        }
                        request.getRemoteUser();
                        System.out.println(request.getRemoteUser());
                        ResponseEntity<ResponseObject> issuer = digitalWalletServiceProxy.getUserProfileDetailsByKid(tenantId, null, request.getRemoteUser());
                        UserDTO issuerUser = null;
                        if (issuer.getBody() != null && issuer.getBody().getCode() == 1) {
                            issuerUser = new ObjectMapper().convertValue(
                                    issuer.getBody().getData(),
                                    new TypeReference<>() {
                                    });
                        }
                        BadgeEmailDetailsDto badgeEmail = BadgeEmailDetailsDto.builder().to(userDTO.getEmail()).firstName(userDTO.getFirstName()).lastName(userDTO.getLastName())
                                .program(model.getProgramName())
                                .issuerName(issuerUser.getFirstName()).createdBy(issuerUser.getUserId()).build();
                        emailList.add(badgeEmail);

                    }
                    emailService.scheduleEmailBadge(emailList);
                }
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "SUCCESS", null);
            }
            return res;
        }
    }

    @Override
    public ResponseObject bulkProgram(String tenantId, MultipartFile file) throws Exception {

        Reader reader = new InputStreamReader(file.getInputStream());
        try (CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {
            List<String[]> rows = csvReader.readAll();
            for (String[] row : rows) {
                AddProgramDTO programDto = new AddProgramDTO();
                programDto.setProgramName(row[0]);
                programDto.setDescription(row[1]);
                programDto.setProgramDuration(row[2]);
                programDto.setStartDate(row[3]);
                programDto.setEndDate(row[4]);
                programDto.setBadgeName(row[5]);
                programDto.setEarningRequirement(row[6]);
                programDto.setStandard(row[7]);
                programDto.setIssuedBy(row[8]);
                programDto.setAdditionalDetailName(row[9]);
                programDto.setEvidenceName(row[10]);
                programDto.setCreatedById(row[11]);
                addPrograms(tenantId, "", programDto);
            }
        }
        return new ResponseObject(ConstantUtil.SUCCESS_CODE, "SUCCESS", null);


    }


    public Boolean addPrograms(String tenantId, String userId, AddProgramDTO programDto) {

        try {
            ProgramsModel programsModel = new ProgramsModel();

            if (tenantId != null) {
                programsModel.setTenantId(tenantId);
            }
            programsModel.setProgramName(programDto.getProgramName());
            programsModel.setDescription(programDto.getDescription());
            programsModel.setIssuedBy(programDto.getIssuedBy());
            programsModel.setEndDate(programDto.getEndDate());
            programsModel.setStartDate(programDto.getStartDate());

            List<Evidence> evidence = evidenceRepository.findAllByTenantIdAndName(tenantId, programDto.getEvidenceName());
            if (evidence != null && evidence.size() > 0) {
                programsModel.setEvidence(evidence.get(0));
            } else {
                Evidence ev = new Evidence();
                ev.setName(programDto.getEvidenceName());
                evidenceRepository.save(ev);
                programsModel.setEvidence(ev);
            }


            List<AdditionalDetails> additionalDetails = additionalDetailsRepository.findAllByTenantIdAndName(tenantId, programDto.getAdditionalDetailName());
            if (additionalDetails != null && additionalDetails.size() > 0) {
                programsModel.setAdditionalDetails(additionalDetails.get(0));
            } else {
                AdditionalDetails ad = new AdditionalDetails();
                ad.setName(programDto.getAdditionalDetailName());
                additionalDetailsRepository.save(ad);
                programsModel.setAdditionalDetails(ad);
            }


            List<Standard> standard = standardRepository.findByTenantIdAndName(tenantId, programDto.getStandard());
            if (standard != null && standard.size() > 0) {
                programsModel.setStandard(standard.get(0));
            } else {
                Standard stan = new Standard();
                stan.setName(programDto.getStandard());
                stan.setIsActivated(true);
                standardRepository.save(stan);
                programsModel.setStandard(stan);
            }

            List<EarningRequirement> earningRequirement = earningRequirementRepository.findAllByTenantIdAndName(tenantId, programDto.getEarningRequirement());
            if (earningRequirement != null && earningRequirement.size() > 0) {
                programsModel.setEarningRequirement(earningRequirement.get(0));
            } else {
                EarningRequirement ear = new EarningRequirement();
                ear.setName(programDto.getEarningRequirement());
                earningRequirementRepository.save(ear);
                programsModel.setEarningRequirement(ear);
            }
            programsModel = programRepository.save(programsModel);


            if (programDto.getBadgeName() != null) {
                ProgramBadge programBadge = new ProgramBadge();
                List<BadgesModel> badgesModel = badgeRepository.findByTenantIdAndBadgeName(tenantId, programDto.getBadgeName());
                if (badgesModel != null && badgesModel.size() > 0) {
                    programBadge.setBadgesModel(badgesModel.get(0));
                } else {
                    BadgesModel badge = new BadgesModel();
                    badge.setBadgeName("");
                    badgeRepository.save(badge);
                    programBadge.setBadgesModel(badge);

                }
                programBadge.setProgramsModel(programsModel);
                programBadgeRepository.save(programBadge);
            }
            programRepository.save(programsModel);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
