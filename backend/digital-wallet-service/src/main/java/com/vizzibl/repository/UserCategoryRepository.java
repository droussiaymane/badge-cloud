package com.vizzibl.repository;

import com.vizzibl.entity.UserCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategoryModel, Long> {
    List<UserCategoryModel> getAllByTenantId(String tenantId);

    Optional<UserCategoryModel> findByCategory(String categoryName);
}
