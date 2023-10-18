package com.vizzibl.service.dashboard;

import com.vizzibl.proxy.ProgramServiceProxy;
import com.vizzibl.repository.StudentCollectionsRepository;
import com.vizzibl.repository.UserCategoryRepository;
import com.vizzibl.repository.UsersRepository;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.utils.ConstantUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardStatisticServiceImpl implements DashboardStatisticService {

    final StudentCollectionsRepository studentCollectionsRepo;
    final UsersRepository usersRepository;
    final UserCategoryRepository userCategoryRepo;
    final ProgramServiceProxy programServiceProxy;

    @Override
    public ResponseEntity<ResponseObject> getDashboardStatistics() {
        long collectionCount = studentCollectionsRepo.count();
        //students
        long studentsCount = usersRepository.countByUserCategoryModel(userCategoryRepo.findById(2L).get());
        //instructors
        long instructorsCount = usersRepository.countByUserCategoryModel(userCategoryRepo.findById(3L).get());
        Map<String, Object> totalCounts = new HashMap<>();
        totalCounts.put("collectionCount", collectionCount);
        totalCounts.put("studentCount", studentsCount);
        totalCounts.put("instructorCount", instructorsCount);
        ResponseEntity<ResponseObject> response = programServiceProxy.getTotalNumberOfPrograms();
        ResponseObject resObj = response.getBody();
        totalCounts.put("programCount", resObj.getData());
        var res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "success", totalCounts);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
