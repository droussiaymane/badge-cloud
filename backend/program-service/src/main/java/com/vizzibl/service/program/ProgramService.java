package com.vizzibl.service.program;


import com.vizzibl.dto.AddProgramDTO;
import com.vizzibl.dto.AssignProgramDTO;
import com.vizzibl.dto.DeleteStudentProgramDTO;
import com.vizzibl.dto.EnOrDisCollectionDTO;
import com.vizzibl.response.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface ProgramService {

    ResponseObject addProgram(String tenant, String userId, AddProgramDTO programDto);

    ResponseObject updateProgram(String tenantId, String userId, String programId, AddProgramDTO programDto);


    ResponseObject deleteProgram(String tenant, Long programId);

    ResponseObject getProgramsList(String tenant, Integer page, Integer limit);

    ResponseObject searchByProgramName(String tenant, String programName);

    ResponseObject findById(String tenant, Long id);

    ResponseObject findProgramByStudentId(String tenant, Long studentId);

    ResponseObject assignProgram(String tenantId, String userId, AssignProgramDTO assign);

    ResponseObject getBadgeByProgramId(String tenantId, String userId, Long programId);

    ResponseObject getBadgesByStudent(String tenantId, String userId, Long studentId);

    ResponseObject enableOrDisableCollection(String tenantId, EnOrDisCollectionDTO dto);

    ResponseObject deleteStudentFromProgram(String tenantId, DeleteStudentProgramDTO dto);

    ResponseObject getStudentListByProgramId(String tenantId, Long programId, Integer page, Integer limit);

    ResponseObject getTotalNumberOfPrograms();

    ResponseObject assignProgramBulk(String tenantId, MultipartFile file) throws Exception;

    ResponseObject enableOrDisableBulk(String tenantId, MultipartFile file) throws Exception;

    ResponseObject bulkProgram(String tenantId, MultipartFile file) throws Exception;
}
