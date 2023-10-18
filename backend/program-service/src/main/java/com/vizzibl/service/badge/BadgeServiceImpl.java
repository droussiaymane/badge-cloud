package com.vizzibl.service.badge;


import com.vizzibl.common.ConstantUtil;
import com.vizzibl.common.FileUtils;
import com.vizzibl.dto.AddBadgeDTO;
import com.vizzibl.dto.BadgeDTO;
import com.vizzibl.entity.BadgesModel;
import com.vizzibl.repository.BadgeRepository;
import com.vizzibl.response.ResponseObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;

    @Value("${badges.image.path}")
    private String badgeImagesPath;

    @Value("${badges.image.path.upload}")
    private String badgeImagesUploadPath;

    public BadgeServiceImpl(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    @Override
    public ResponseObject addOrUpdateBadge(String tenant, String userId, AddBadgeDTO badgeDTO) {

        ResponseObject res;
        BadgesModel badgesModel;
        try {
            if (badgeDTO.getId() != null) {
                Optional<BadgesModel> badgesModel1 = badgeRepository.findByTenantIdAndId(tenant, badgeDTO.getId());
                badgesModel = badgesModel1.orElseGet(() ->
                {
                    BadgesModel bm = new BadgesModel();
                    bm.setCreatedBy(userId);
                    bm.setDate(LocalDateTime.now());
                    bm.setTenantId(tenant);
                    return bm;
                });

            } else {
                badgesModel = new BadgesModel();
                badgesModel.setCreatedBy(userId);
                badgesModel.setDate(LocalDateTime.now());
                badgesModel.setTenantId(tenant);
            }
            if (badgeDTO.getBadgeName() != null && !badgeDTO.getBadgeName().isEmpty()) {
                badgesModel.setBadgeName(badgeDTO.getBadgeName());
            }
            if (badgeDTO.getFile() != null && !badgeDTO.getFile().isEmpty()) {
                String fileName = FileUtils.renameFile(Objects.requireNonNull(badgeDTO.getFile().getOriginalFilename()), "Badge-", UUID.randomUUID().toString());
                FileUtils.createFile(badgeImagesUploadPath + "/" + tenant, fileName, badgeDTO.getFile().getInputStream());
                badgesModel.setFileName(fileName);
            } else if (badgeDTO.getId() == null) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "Badge file is not found!!!");
                return res;
            }
            badgeRepository.save(badgesModel);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Badge Successfully Added/Updated");
        } catch (IOException e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator!!!");
        }
        return res;
    }

    @Override
    public ResponseObject getBadgesList(String tenantId, Integer page, Integer limit) {
        ResponseObject res;
        try {
            Page<BadgesModel> badgesModelPage = badgeRepository.findAllByTenantId(tenantId, PageRequest.of(page, limit));
            if (badgesModelPage.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }
            badgesModelPage.getContent().forEach(x ->
                    {
                        x.setFileName(badgeImagesPath + "/" + tenantId + "/" + x.getFileName());
                        x.setProgramBadge(null);
                        x.setStudentCollections(null);
                    }
            );
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "success", badgesModelPage);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error occurred please contact your administrator" + e);
        }
        return res;
    }

    @Override
    public ResponseObject getBadgesByBadgeName(String tenant, String badgeName) {
        ResponseObject res;
        try {
            List<BadgesModel> badgesModelPage = badgeRepository.findByTenantIdAndBadgeName(tenant, badgeName);
            if (badgesModelPage.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }
            List<BadgesModel> badgesModels = badgesModelPage.stream().filter(f -> f.getProgramBadge() == null)
                    .map(x -> {
                        x.setFileName(badgeImagesPath + "/" + tenant + "/" + x.getFileName());
                        return x;
                    }).toList();
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "success", badgesModels);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error occurred please contact your administrator");
        }
        return res;
    }

    private ResponseObject prepareResponse(List<BadgesModel> badges, String tenantId) {

        ResponseObject res;
        List<BadgeDTO> badgeDTOS;
        if (badges.isEmpty()) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
        } else {
            badgeDTOS = badges.stream().map(b -> {
                BadgeDTO dto = new BadgeDTO();
                BeanUtils.copyProperties(b, dto);
                dto.setImage(badgeImagesPath + "/" + tenantId + "/" + b.getFileName());
                return dto;
            }).toList();
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", badgeDTOS);
        }
        return res;
    }

    @Override
    public ResponseObject deleteBadge(String tenant, Long id) {
        ResponseObject res;
        try {
            badgeRepository.deleteById(id);
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Badge Successfully Deleted!!!");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred, please contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject findById(String tenant, Long id) {

        ResponseObject res;
        BadgeDTO dto = new BadgeDTO();
        try {

            Optional<BadgesModel> optional = badgeRepository.findById(id);
            if (optional.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "Badge Not Found!!!");
            }

            BadgesModel badge = optional.get();
            BeanUtils.copyProperties(badge, dto);

            dto.setImage(badgeImagesPath + "/" + tenant + "/" + badge.getFileName());
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dto);
        } catch (Exception e) {

            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred, please contact your Administrator");
        }
        return res;
    }

    @Override
    public ResponseObject getUnmappedEntries(String tenant) {

        ResponseObject res;

        try {

            res = prepareResponse(badgeRepository.findAllUnmappedEntries(tenant), tenant);
        } catch (Exception e) {

            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred, please contact your Administrator");
        }
        return res;
    }
}
