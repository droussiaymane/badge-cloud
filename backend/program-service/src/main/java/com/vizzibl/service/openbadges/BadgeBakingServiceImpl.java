package com.vizzibl.service.openbadges;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vizzibl.config.BadgeCloudConfig;
import com.vizzibl.dto.UserDTO;
import com.vizzibl.entity.BadgesModel;
import com.vizzibl.entity.ExpireRevokeStatus;
import com.vizzibl.entity.OpenBadgeMappingModel;
import com.vizzibl.entity.ProgramsModel;
import com.vizzibl.entity.assertion.*;
import com.vizzibl.proxy.DigitalWalletServiceProxy;
import com.vizzibl.repository.AssertionModelRepository;
import com.vizzibl.repository.BadgeRepository;
import com.vizzibl.repository.ExpireRevokeStatusRepository;
import com.vizzibl.repository.ProgramRepository;
import com.vizzibl.response.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BadgeBakingServiceImpl implements BadgeBakingService {


    private final AssertionModelRepository assertionRepository;
    private final DigitalWalletServiceProxy digitalWalletServiceProxy;
    private final BadgeRepository badgeRepository;
    private final BadgeCloudConfig badgeConfig;
    private final ProgramRepository programRepository;
    private final Environment environment;
    private final ExpireRevokeStatusRepository expireRevokeStatusRepository;

    private final HttpServletRequest httpServletRequest;


    public BadgeBakingServiceImpl(AssertionModelRepository assertionRepository, DigitalWalletServiceProxy digitalWalletServiceProxy, Environment environment, BadgeRepository badgeRepository, BadgeCloudConfig badgeConfig, ProgramRepository programRepository, ExpireRevokeStatusRepository expireRevokeStatusRepository, HttpServletRequest httpServletRequest) {
        this.assertionRepository = assertionRepository;
        this.digitalWalletServiceProxy = digitalWalletServiceProxy;
        this.environment = environment;
        this.badgeRepository = badgeRepository;
        this.badgeConfig = badgeConfig;
        this.programRepository = programRepository;
        this.expireRevokeStatusRepository = expireRevokeStatusRepository;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ResponseEntity<String> getAssertion(String assertionKey) throws JsonProcessingException {

        OpenBadgeMappingModel badgeMappingModel = assertionRepository.findByAssertionKey(assertionKey.trim());
        if (badgeMappingModel == null)
            throw new RuntimeException("No Data Found!!");
        ObjectMapper mapper = new ObjectMapper();
        String baseUrl = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(), "");
        List<ExpireRevokeStatus> expireRevokeStatuses = expireRevokeStatusRepository.findByStudentIdAndProgramId(badgeMappingModel.getStudentId(), badgeMappingModel.getProgramId());

        if (!expireRevokeStatuses.isEmpty() && expireRevokeStatuses.get(0) != null && expireRevokeStatuses.get(0).isRevokeStatus()) {
            RevokedAssertionBakingDTO assertionDto = new RevokedAssertionBakingDTO();
            assertionDto.setType("Assertion");
            assertionDto.setId(baseUrl + badgeConfig.getAssertionUrl() + badgeMappingModel.getAssertionModel().getAssertionKey());
            assertionDto.setContext(badgeConfig.getContext());
            assertionDto.setRevoked(expireRevokeStatuses.get(0).isRevokeStatus());
            if (!expireRevokeStatuses.get(0).getRevokeReason().isEmpty() && expireRevokeStatuses.get(0).getRevokeReason() != null) {
                assertionDto.setRevocationReason(expireRevokeStatuses.get(0).getRevokeReason());
            }
            return new ResponseEntity<>(mapper.writeValueAsString(assertionDto), HttpStatus.OK);
        }

        return new ResponseEntity<>(mapper.writeValueAsString(createAssertion(badgeMappingModel)), HttpStatus.OK);
    }

    @Override
    public String getBadge(String badgeKey) throws JsonProcessingException {
        OpenBadgeMappingModel badgeMappingModel = assertionRepository.findByAssertionKey(badgeKey.trim());
        if (badgeMappingModel == null) {
            throw new RuntimeException("No Data Found!!");
        }
        Optional<BadgesModel> badgesModel = badgeRepository.findById(badgeMappingModel.getBadgeId());
        if (badgesModel.isEmpty()) {
            throw new RuntimeException("No Data Found!!");
        }

        String baseUrl = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(), "");
        Badge badge = new Badge();
        Criteria criteria = new Criteria();
        Optional<ProgramsModel> programsModel = programRepository.findById(badgeMappingModel.getProgramId());
        if (programsModel.isEmpty()) {
            badge.setDescription("This badge is awarded for passing the 3-D printing knowledge and safety test.");
            criteria.setNarrative("Students are tested on knowledge and safety, both through a paper test and a supervised performance evaluation on live equipment");
        } else {
            criteria.setNarrative(programsModel.get().getEarningRequirement().getDescription());
            badge.setDescription(programsModel.get().getDescription());
        }
        badge.setType("BadgeClass");
        badge.setContext(badgeConfig.getContext());
        badge.setId(baseUrl + badgeConfig.getBadgeUrl() + badgeKey);
        badge.setName(badgesModel.get().getBadgeName());
        //needs to know
        String filePath = badgeMappingModel.getFileName().replace("/opt/vizzibl", "");
        badge.setImage(environment.getProperty("file-server.base-url") + filePath);
        //needs to know
        badge.setCriteria(criteria);

        //todo: set them dynamic

        Issuer issuer = new Issuer();
        issuer.setContext(badgeConfig.getContext());
        issuer.setName(badgeConfig.getIssuerName());
        issuer.setUrl(badgeConfig.getIssuerUrl());
        issuer.setEmail(badgeConfig.getIssuerEmail());
        issuer.setId(baseUrl + badgeConfig.getIssuerId());
        issuer.setType("Profile");
        badge.setIssuer(issuer);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(badge);

    }

    @Override
    public AssertionBakingDTO createAssertion(OpenBadgeMappingModel badgeMappingModel) {

        ResponseEntity<ResponseObject> responseObject = digitalWalletServiceProxy.findByUserId(badgeMappingModel.getTenantId(), null, badgeMappingModel.getStudentId().toString());

        ResponseObject response = responseObject.getBody();
        UserDTO userDTO;
        if (response != null && response.getCode() == 1) {
            userDTO = new ObjectMapper().convertValue(
                    response.getData(),
                    new TypeReference<>() {
                    });
        } else {
            throw new RuntimeException("User Data Found!!");
        }

        String baseUrl = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(), "");

        Optional<ExpireRevokeStatus> expireRevokeStatus = expireRevokeStatusRepository.findByTenantIdAndStudentIdAndProgramId(badgeMappingModel.getTenantId(), badgeMappingModel.getStudentId(), badgeMappingModel.getProgramId());
        AssertionBakingDTO assertionDto = new AssertionBakingDTO();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX");
        if (expireRevokeStatus.isPresent()) {
            assertionDto.setRevoked(expireRevokeStatus.get().isRevokeStatus());
            if (expireRevokeStatus.get().getExpires() != null) {
                assertionDto.setExpires(expireRevokeStatus.get().getExpires().atOffset(ZoneOffset.UTC).format(dtf));
            }
        }

        assertionDto.setType("Assertion");
        assertionDto.setId(baseUrl + badgeConfig.getAssertionUrl() + badgeMappingModel.getAssertionModel().getAssertionKey());
        assertionDto.setContext(badgeConfig.getContext());

        assertionDto.setIssuedOn(badgeMappingModel.getAssertionModel().getIssuedOn().atOffset(ZoneOffset.UTC).format(dtf));
        String filePath = badgeMappingModel.getFileName().replace("/opt/vizzibl", "");
        assertionDto.setImage(environment.getProperty("file-server.base-url") + filePath);
        assertionDto.setBadge(baseUrl + badgeConfig.getBadgeUrl() + badgeMappingModel.getAssertionModel().getAssertionKey());
        AssertionRecipient recipient = new AssertionRecipient();

        //  StandardPasswordEncoder encoder = new StandardPasswordEncoder(Objects.requireNonNull(environment.getProperty("salt")));
        //  String encode = encoder.encode(usersModel.getEmail());

        recipient.setType("email");
//        recipient.setSalt(environment.getProperty("salt"));
        recipient.setHashed(false);
        recipient.setIdentity(userDTO.getEmail());
        assertionDto.setRecipient(recipient);

        AssertionVerification verification = new AssertionVerification();
        verification.setType("hosted");
        assertionDto.setVerification(verification);

        return assertionDto;
    }

    @Override
    public String getIssuer(String issuerKey) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if (issuerKey != null && issuerKey.equals("032cf832-c4ca-4eb9-99c5-7a7adf308e6d")) {
            Issuer issuer = new Issuer();
            String baseUrl = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(), "");
            issuer.setContext(badgeConfig.getContext());
            issuer.setName(badgeConfig.getIssuerName());
            issuer.setUrl(badgeConfig.getIssuerUrl());
            issuer.setEmail(badgeConfig.getIssuerEmail());
            issuer.setId(baseUrl + badgeConfig.getIssuerId());
            issuer.setType("Profile");
            return mapper.writeValueAsString(issuer);
        }
        return mapper.writeValueAsString("Invalid Id for Issuer!");
    }
}
