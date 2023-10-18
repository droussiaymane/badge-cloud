package com.vizzibl.repository;


import com.vizzibl.entity.ProgramsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<ProgramsModel, Long> {

    @Query(value = "select p from ProgramsModel  p where p.tenantId = :tenantId")
    List<ProgramsModel> getAllByTenantIdWithoutPagination(String tenantId);


    Page<ProgramsModel> findAllByTenantId(String tenantId, Pageable pageable);

    Page<ProgramsModel> findByTenantId(String tenantId, Pageable pageable);

    Optional<ProgramsModel> findByIdAndTenantId(Long id, String tenantId);

    @Query(value = "select p from ProgramsModel  p where p.tenantId = :tenantId and  p.programName LIKE CONCAT('%',:programName,'%')")
    List<ProgramsModel> findAllByTenantIdAndProgramBadge(String tenantId, String programName);

    @Query(value = "select p from ProgramsModel p where p.tenantId = :tenantId and p.id in( select st.program.id from StudentProgram st where st.studentId = :studentId and st.status = 1 ) ")
    List<ProgramsModel> getAllByStudentId(String tenantId, Long studentId);

    @Query(value = "select p from ProgramsModel p where p.tenantId = :tenantId and p.id in( select st.program.id from StudentProgram st where st.studentId = :studentId  ) ")
    List<ProgramsModel> findAllByStudentId(String tenantId, Long studentId);

    ProgramsModel findByProgramName(String programName);
}
