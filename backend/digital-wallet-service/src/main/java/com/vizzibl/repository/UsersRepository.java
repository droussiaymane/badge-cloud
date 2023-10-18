package com.vizzibl.repository;

import com.vizzibl.entity.UserCategoryModel;
import com.vizzibl.entity.UsersModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersModel, Long> {

    Page<UsersModel> findAllByTenantId(String tenantId, Pageable pageable);

    UsersModel findByUserNameAndPassword(String userName, String password);

    List<UsersModel> findAllByUserNameContaining(String userName);

    List<UsersModel> findAllByFirstNameContaining(String firstName);

    List<UsersModel> findAllByLastNameContaining(String surName);

    List<UsersModel> findAllByUserIdContaining(String userId);


    List<UsersModel> findAllByUserCategoryModelIn(List<UserCategoryModel> categoryModels);

    List<UsersModel> findAllByUserCategoryModel(UserCategoryModel userCategoryModel);

    UsersModel findByUserName(String userName);
//    @Query(value = "select u from UsersModel u where u.userId = :userId and u.tenantId = :tenantId")
//    List<UsersModel> findByUserIdAndTenantId(String userId,String tenantId);

    //    Optional<UsersModel> findByUserIdAndTenantId(String userId,String tenantId);
//    @Query(value = "select u from UsersModel u where u.userId = :userId and u.tenant.tenantId = :tenantId")
    Optional<UsersModel> findByIdAndTenantId(String userId, String tenantId);

    List<UsersModel> findByTenantIdAndIdIn(String tenantId, List<Long> userId);

    //    Optional<UsersModel> findByTenantIdAndUserIdIn(String tenantId,List<String> userIds);
    @Query(value = "select u from UsersModel u where u.tenantId = :tenantId and u.firstName LIKE CONCAT('%',:userName,'%')")
    List<UsersModel> findByUserNameAndTenantId(String tenantId, String userName);

//    @Query(value = "select u from UsersModel u where u.tenant.id = :tenantId")
//    List<UsersModel> findAllByTenantId(String tenantId);


    @Query(value = "select u from UsersModel u where u.tenantId = :tenantId and u.userCategoryModel.category = :category and (u.firstName LIKE CONCAT('%',:userName,'%') or u.lastName LIKE CONCAT('%',:userName,'%'))")
    List<UsersModel> findByUserCategoryAndUserName(String tenantId, String category, String userName);

    @Query(value = "select sp.student from StudentProgram sp where sp.student.tenantId = :tenantId and sp.programId = :programId")
    List<UsersModel> findAllByProgramId(Long programId, String tenantId);

    Optional<UsersModel> findById(Long id);

    Optional<UsersModel> getBykID(String sub);

    long countByUserCategoryModel(UserCategoryModel userCategoryModel);

    List<UsersModel> findByUserCategoryModelAndEmailIn(UserCategoryModel categoryModel, List<String> emails);
}
