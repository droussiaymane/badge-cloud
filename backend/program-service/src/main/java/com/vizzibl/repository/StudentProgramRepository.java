package com.vizzibl.repository;


import com.vizzibl.entity.ProgramsModel;
import com.vizzibl.entity.StudentProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudentProgramRepository extends JpaRepository<StudentProgram, Long> {


    List<StudentProgram> findAllByProgramAndStatus(ProgramsModel programsModel, int status);

    Optional<StudentProgram> findByProgramIdAndStudentId(Long programId, Long studentId);

    @Query(value = "select sp from StudentProgram sp where sp.program.tenantId = :tenantId and sp.program.id = :programId")
    Page<StudentProgram> findAllByTenantIdAndProgramId(String tenantId, Long programId, Pageable pageable);

    @Modifying
    @Query(value = "delete from StudentProgram sp where sp.studentId = :studentId ")
    void deleteByStudentId(Long studentId);
}
