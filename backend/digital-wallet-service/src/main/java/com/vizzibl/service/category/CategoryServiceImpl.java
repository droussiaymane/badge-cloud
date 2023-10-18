package com.vizzibl.service.category;

import com.vizzibl.entity.UserCategoryModel;
import com.vizzibl.repository.UserCategoryRepository;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.utils.ConstantUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final UserCategoryRepository userCategoryRepository;

    public CategoryServiceImpl(UserCategoryRepository userCategoryRepository) {
        this.userCategoryRepository = userCategoryRepository;
    }

    @Override
    public ResponseObject getAllByTenantId(String tenantId) {
        ResponseObject res;
        try {
            List<UserCategoryModel> userCategoryModel = userCategoryRepository.findAll();
            if (userCategoryModel.isEmpty()) {
                return new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found!!!");
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "success", userCategoryModel);
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error!!! Please try again later or contact your Administrator");
        }
        return res;
    }
}
