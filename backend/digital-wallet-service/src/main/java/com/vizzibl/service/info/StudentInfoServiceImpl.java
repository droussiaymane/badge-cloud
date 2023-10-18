package com.vizzibl.service.info;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vizzibl.dto.ProgramBadgeDTO;
import com.vizzibl.dto.StudentHomeDTO;
import com.vizzibl.entity.UsersModel;
import com.vizzibl.proxy.ProgramServiceProxy;
import com.vizzibl.repository.UsersRepository;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.utils.ConstantUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentInfoServiceImpl implements StudentInfoService {

    @Value("${user.image.path}")
    private String userImagePath;
    private final UsersRepository usersRepository;
    private final ProgramServiceProxy programServiceProxy;

    public StudentInfoServiceImpl(UsersRepository usersRepository, ProgramServiceProxy programServiceProxy) {
        this.usersRepository = usersRepository;
        this.programServiceProxy = programServiceProxy;
    }

    @Override
    public ResponseObject getStudentAndBadgesInfo(String tenantId, String userId, Long studentId) {

        ResponseObject responseObject;
        StudentHomeDTO home = new StudentHomeDTO();
        try {
            ResponseEntity<ResponseObject> re = programServiceProxy.getStudentAndBadgesInfo(tenantId, userId, studentId);
            ResponseObject res = re.getBody();
            if (res != null && res.getCode() != 0 && res.getData() != null) {

                List<ProgramBadgeDTO> programBadgeDTOS = new ObjectMapper().convertValue(
                        res.getData(),
                        new TypeReference<>() {
                        });
                home.setProgramsBadges(programBadgeDTOS);
            }

            Optional<UsersModel> user = usersRepository.findById(studentId);
            if (user.isPresent()) {
                home.setStudentId(user.get().getUserId());
                home.setStudentName(user.get().getFirstName() + " " + user.get().getLastName());
                home.setId(user.get().getId());
                home.setImage(userImagePath + "/" + user.get().getPhoto());
            }
            responseObject = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", home);
        } catch (Exception e) {
            e.printStackTrace();
            responseObject = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!!");
        }
        return responseObject;
    }
}
