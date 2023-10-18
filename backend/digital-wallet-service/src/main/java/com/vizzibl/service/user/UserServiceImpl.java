package com.vizzibl.service.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.vizzibl.dto.*;
import com.vizzibl.entity.*;
import com.vizzibl.proxy.EmailServiceProxy;
import com.vizzibl.proxy.KeycloakServiceProxy;
import com.vizzibl.proxy.ProgramServiceProxy;
import com.vizzibl.repository.*;
import com.vizzibl.response.KeycloakResponseObject;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.utils.ConstantUtil;
import com.vizzibl.utils.FileUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    @Autowired
    KeycloakServiceProxy keycloakServiceProxy;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    UserCategoryRepository userCategoryRepository;
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    NewUsersRepository newUsersRepository;

    @Value("${user.image.path}")
    String imagePath;
    @Autowired
    ProgramServiceProxy programProxy;
    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    EmailServiceProxy emailServiceProxy;


    @Override
//    @Transactional
    public ResponseObject addUser(String tenant, String userId, AddUserDTO dto) {

        ResponseObject res;
        UsersModel user = new UsersModel();
        List<UserRegistrationDto> userRegEmail = new ArrayList<>();
        try {
            CreateUserDto createUserDto = new CreateUserDto();
            createUserDto.setEmail(dto.getEmail());
            createUserDto.setPassword(dto.getPassword());
            createUserDto.setFirstName(dto.getFirstName());
            createUserDto.setUsername(dto.getUserName());
            createUserDto.setLastName(dto.getLastName());
            Optional<UserCategoryModel> categoryModel = userCategoryRepository.findByCategory(dto.getCategoryName());
            if (categoryModel.isPresent()) {
                createUserDto.setRole(dto.getCategoryName());
                user.setUserCategoryModel(categoryModel.get());
            } else {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "Category is not found");
                return res;
            }
            createUserDto.setTenantId(tenant);
            ResponseEntity<KeycloakResponseObject> response = keycloakServiceProxy.addUser(createUserDto);
            if (response.getBody().getCode() == 0) {
                System.out.println(response.getBody());
                res = new ResponseObject(ConstantUtil.ERROR_CODE, response.getBody().getMessage());
            } else {
                log.info("kID" + response.getBody().getKID());
                dto.setKID(response.getBody().getKID());
                BeanUtils.copyProperties(dto, user);
                user.setLastName(dto.getLastName());
                Optional<Tenant> optionalTenant = tenantRepository.findByTenantId(tenant);
                if (optionalTenant.isPresent()) {
                    user.setTenantId(optionalTenant.get().getTenantId());
                }

                if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {

                    String fileName = FileUtils.renameFile(Objects.requireNonNull(dto.getPhoto().getOriginalFilename()), user.getFirstName(), user.getId());
                    FileUtils.createFile(imagePath + "/" + optionalTenant.get().getTenantId(), fileName, dto.getPhoto().getInputStream());
                    user.setPhoto(optionalTenant.get().getTenantId() + "/" + fileName);
                }
                user.setPassword(null);
                user = usersRepository.save(user);
                UserRegistrationDto userEmailDto = UserRegistrationDto.builder().userName(user.getUserName()).to(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName())
                        .program("").password(dto.getPassword()).build();
                userRegEmail.add(userEmailDto);
                ResponseEntity<ResponseObject> emailRes = emailServiceProxy.sendRegistrationsEmail(userRegEmail);
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "User Successfully Added", user);
            }
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator : " + e);
        }
        return res;
    }

    @Override
    public ResponseObject updateUser(String tenant, String headerUserId, String userId, AddUserDTO dto) {

        ResponseObject res;
        UsersModel user;
        try {
            Optional<UsersModel> usersModel = usersRepository.findByIdAndTenantId(userId, tenant);
            if (usersModel.isPresent()) {
                user = usersModel.get();
                CreateUserDto createUserDto = new CreateUserDto();

                if (dto.getFirstName() != null && !dto.getFirstName().isEmpty()) {
                    createUserDto.setFirstName(dto.getFirstName());
                    user.setFirstName(dto.getFirstName());
                }
                if (dto.getLastName() != null && !dto.getLastName().isEmpty()) {
                    createUserDto.setLastName(dto.getLastName());
                    user.setLastName(dto.getLastName());
                }
                if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
                    createUserDto.setEmail(dto.getEmail());
                    user.setEmail(dto.getEmail());
                }
                if (dto.getUserName() != null && !dto.getUserName().isEmpty()) {
                    createUserDto.setUsername(dto.getUserName());
                    user.setUserName(dto.getUserName());
                }
                if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                    createUserDto.setPassword(dto.getPassword());
                    user.setPassword(dto.getPassword());
                }
                if (dto.getTenantId() != null && !dto.getTenantId().isEmpty()) {
                    createUserDto.setTenantId(dto.getTenantId());
                    user.setTenantId(dto.getTenantId());
                }

                ResponseEntity<ResponseObject> response = keycloakServiceProxy.updateUser(createUserDto, user.getKID());
                if (response.getBody().getCode() == 0) {
                    res = new ResponseObject(ConstantUtil.ERROR_CODE, response.getBody().getMessage());
                } else {

                    if (dto.getUserId() != null && !dto.getUserId().isEmpty()) {
                        user.setUserId(dto.getUserId());
                    }
                    Optional<Tenant> optionalTenant = tenantRepository.findByTenantId(tenant);
                    if (optionalTenant.isPresent()) {
                        user.setTenantId(optionalTenant.get().getTenantId());
                    }
                    Optional<UserCategoryModel> categoryModel = userCategoryRepository.findByCategory(dto.getCategoryName());
                    if (categoryModel.isPresent()) {
                        user.setUserCategoryModel(categoryModel.get());
                    }

                    if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
                        String fileName = FileUtils.renameFile(Objects.requireNonNull(dto.getPhoto().getOriginalFilename()), user.getFirstName(), user.getId());
                        FileUtils.createFile(imagePath + "/" + optionalTenant.get().getTenantId(), fileName, dto.getPhoto().getInputStream());
                        user.setPhoto(optionalTenant.get().getTenantId() + "/" + fileName);
                    }
                    usersRepository.save(user);
                    res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "User Successfully updated");
                }
                return res;
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "User not found");
        } catch (Exception e) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator : " + e);
        }
        return res;
    }

    @Override
    public ResponseObject updateUserProfile(String tenant, String headerUserId, String userId, UpdateUserProfileDTO dto) {

        ResponseObject res;
        try {
            Optional<UsersModel> usersModel = usersRepository.findByIdAndTenantId(userId, tenant);

            if (usersModel.isPresent()) {
                UsersModel usersModel1 = usersModel.get();
                UserProfile userProfile;

                if (usersModel1.getUserProfile() != null) {
                    userProfile = usersModel1.getUserProfile();
                } else {
                    userProfile = new UserProfile();
                }
                if (dto.getFirstName() != null && !dto.getFirstName().isEmpty()) {
                    usersModel1.setFirstName(dto.getFirstName());
                }
                if (dto.getLastName() != null && !dto.getLastName().isEmpty()) {
                    usersModel1.setLastName(dto.getLastName());
                }
                if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
                    usersModel1.setEmail(dto.getEmail());
                }
                if (dto.getMobileNumber() != null && !dto.getMobileNumber().isEmpty()) {
                    userProfile.setMobileNumber(dto.getMobileNumber());
                }
                if (dto.getCurrentEmployer() != null && !dto.getCurrentEmployer().isEmpty()) {
                    userProfile.setCurrentEmployer(dto.getCurrentEmployer());
                }
                if (dto.getBirthYear() != null && !dto.getCurrentEmployer().isEmpty()) {
                    userProfile.setBirthYear(dto.getBirthYear());
                }
                if (dto.getBadgeCloudURL() != null && !dto.getBadgeCloudURL().isEmpty()) {
                    userProfile.setBadgeCloudURL(dto.getBadgeCloudURL());
                }
                if (dto.getBio() != null && !dto.getBio().isEmpty()) {
                    userProfile.setBio(dto.getBio());
                }
                if (dto.getState() != null && !dto.getState().isEmpty()) {
                    userProfile.setState(dto.getState());
                }
                if (dto.getCity() != null && !dto.getCity().isEmpty()) {
                    userProfile.setCity(dto.getCity());
                }
                if (dto.getCountry() != null && !dto.getCountry().isEmpty()) {
                    userProfile.setCountry(dto.getCountry());
                }
                if (dto.getZipCode() != null && !dto.getZipCode().isEmpty()) {
                    userProfile.setZipCode(dto.getZipCode());
                }
                if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
                    String fileName = FileUtils.renameFile(Objects.requireNonNull(dto.getPhoto().getOriginalFilename()), usersModel1.getFirstName(), usersModel1.getId());
                    FileUtils.createFile(imagePath + "/" + tenant, fileName, dto.getPhoto().getInputStream());
                    usersModel1.setPhoto(tenant + "/" + fileName);
                }
                usersModel1.setUserProfile(userProfile);
                usersRepository.save(usersModel1);
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success");
                return res;
            }
            res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "User not found");

        } catch (Exception exception) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator : " + exception);

        }
        return res;
    }

    @Override
    public ResponseObject getUserProfileByKid(String tenant, String headerUserId, String sub) {
        ResponseObject res;

        try {
            Optional<UsersModel> optional = usersRepository.getBykID(sub);

            if (optional.isEmpty()) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "User Not Found!!!");
            } else {
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", optional.get().getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator");
        }

        return res;
    }

    @Override
    public ResponseObject getUserProfile(String tenant, String headerUserId, String userId) {
        ResponseObject res;

        try {
            Optional<UsersModel> optional = usersRepository.findById(Long.valueOf(userId));

            if (optional.isEmpty()) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "User Not Found!!!");
            } else {
                UsersModel user = optional.get();
                UserProfileDTO dto = new UserProfileDTO();
                BeanUtils.copyProperties(user, dto);
                if (user.getUserProfile() != null) {
                    BeanUtils.copyProperties(user.getUserProfile(), dto);
                }
                if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
                    dto.setPhoto(imagePath + "/" + user.getPhoto());
                }
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator");
        }

        return res;
    }


    @Override
    public ResponseObject findByUserId(String tenant, String id) {

        ResponseObject res;

        try {
            System.out.println("tenant : " + tenant + "  id : " + id);

            Optional<UsersModel> optional = usersRepository.findById(Long.valueOf(id));

            if (optional.isEmpty()) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "User Not Found!!!");
            } else {

                UsersModel user = optional.get();
                UserDTO dto = new UserDTO();
                BeanUtils.copyProperties(user, dto);
                dto.setTenantId(user.getTenantId());
                dto.setCategory(user.getUserCategoryModel().getCategory());
                dto.setPhoto(imagePath + "/" + user.getPhoto());
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator");
        }

        return res;
    }

    @Override
    public ResponseObject getUserByUserIds(String tenant, List<Long> userIds) {

        ResponseObject res;

        try {
            List<UsersModel> usersModels = usersRepository.findByTenantIdAndIdIn(tenant, userIds);

            if (usersModels.isEmpty()) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "User Not Found!!!");
            } else {
                List<UserDTO> userDTOList = usersModels.stream().map(um -> {
                    UserDTO dto = new UserDTO();
                    BeanUtils.copyProperties(um, dto);
                    dto.setTenantId(um.getTenantId());
                    dto.setCategory(um.getUserCategoryModel().getCategory());
                    dto.setPhoto(imagePath + "/" + um.getPhoto());
                    return dto;
                }).collect(Collectors.toList());

                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", userDTOList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Internal server error");
        }
        return res;
    }

    @Override
    public ResponseObject findByCategoryAndUserName(String tenant, String category, String userName) {
        ResponseObject res;

        try {
            List<UsersModel> usersModels = usersRepository.findByUserCategoryAndUserName(tenant, category, userName);

            if (usersModels.isEmpty()) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "User Not Found!!!");
            } else {
                List<UserDTO> userDTOList = usersModels.stream().map(um -> {
                    UserDTO dto = new UserDTO();
                    BeanUtils.copyProperties(um, dto);
                    dto.setTenantId(um.getTenantId());
                    dto.setCategory(um.getUserCategoryModel().getCategory());
                    dto.setPhoto(imagePath + "/" + um.getPhoto());
                    return dto;
                }).collect(Collectors.toList());

                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", userDTOList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator");
        }

        return res;
    }

    @Override
    public ResponseObject deleteById(String tenant, String id) {

        ResponseObject res;

        try {
            Optional<UsersModel> usersModel = usersRepository.findByIdAndTenantId(id, tenant);
            if (usersModel.isPresent()) {
                UsersModel usersModel1 = usersModel.get();
                usersRepository.delete(usersModel1);
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "User Successfully Deleted!!!");
            } else {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "User is not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator!!!");
        }
        return res;
    }

    @Override
    public ResponseObject findStudentsByProgramId(String tenant, Long programId) {

        ResponseObject res;
        if (programId == null || programId == 0) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid User Id!!!");
        } else if (tenant == null || tenant.equals("")) {
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid Tenant/TenantId!!!");
        } else {
            res = prepareResponse(usersRepository.findAllByProgramId(programId, tenant), tenant);
        }
        return res;
    }

    private ResponseObject prepareResponse(List<UsersModel> users, String tenantId) {

        ResponseObject res;
        List<UserDTO> dtos;

        try {
            if (users.isEmpty()) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "No Data Found");
            } else {

                dtos = users.stream().map(u -> {
                    UserDTO dto = new UserDTO();
                    BeanUtils.copyProperties(u, dto);
                    dto.setCategory(u.getUserCategoryModel().getCategory());
                    dto.setPhoto(imagePath + "/" + tenantId + "/" + u.getPhoto());
                    return dto;
                }).toList();

                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dtos);
            }

        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator!!!");
        }

        return res;
    }

    @Override
    public ResponseObject findUsersByTenantId(String tenant, Integer page, Integer limit) {
        ResponseObject res = new ResponseObject();

        try {
            if (tenant == null || tenant.equals("")) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "Invalid Tenant/TenantId!!!");
            } else {
                System.out.println("find by user : tenant " + tenant);
                Page<UsersModel> allUserByTenantId = usersRepository.findAllByTenantId(tenant, PageRequest.of(page, limit));
                res.setData(allUserByTenantId);
                res.setMessage("SUCCESS");
                res.setCode(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator!!!");
        }
        return res;
    }

    @Override
    public ResponseObject getStudentPrograms(String tenant, String userId, Long studentId) {

        ResponseEntity<ResponseObject> res = programProxy.getStudentPrograms(tenant, userId, studentId);
        return res.getBody();
    }

    @Override
    public void bulkCreateUsers(String tenantId, MultipartFile file) throws Exception {
        UUID uuid = UUID.randomUUID();
        Reader reader = new InputStreamReader(file.getInputStream());
        try (CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {
            List<String[]> rows = csvReader.readAll();
            for (String[] row : rows) {
                NewUsers newUser = new NewUsers();
                newUser.setFirstName(row[0]);
                newUser.setLastName(row[1]);
                newUser.setUserId(row[2]);
                newUser.setUserName(row[3]);
                newUser.setEmail(row[4]);
                newUser.setCategory(row[5]);
                newUser.setTenantId(tenantId);
                newUser.setBatchId(uuid.toString());
                newUser.setRegistrationStatus(false);
                newUser.setEmailStatus(false);
                newUsersRepository.save(newUser);
            }
        }
    }

    @Override
    public ResponseObject findByCategoryAndEmails(String category, List<String> emails) {

        ResponseObject res;

        try {
            UserCategoryModel categoryModel = userCategoryRepository.findByCategory(category).get();
            List<UsersModel> usersModels = usersRepository.findByUserCategoryModelAndEmailIn(categoryModel, emails);

            if (usersModels.isEmpty()) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "User Not Found!!!");
            } else {
                List<UserDTO> userDTOList = usersModels.stream().map(um -> {
                    UserDTO dto = new UserDTO();
                    BeanUtils.copyProperties(um, dto);
                    dto.setTenantId(um.getTenantId());
                    dto.setCategory(um.getUserCategoryModel().getCategory());
                    return dto;
                }).collect(Collectors.toList());

                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", userDTOList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Internal server error");
        }
        return res;

    }

    @Override
    public ResponseEntity<Void> triggerUserRegistration() {
        List<NewUsers> usersList = newUsersRepository.findAllByEmailStatusAndRegistrationStatus(false, false);
        List<UserRegistrationDto> userRegEmail = new ArrayList<>();
        UserRegistrationDto email = null;
        for (NewUsers newUser : usersList) {
            email = new UserRegistrationDto();
            UsersModel user = new UsersModel();

            try {
                CreateUserDto createUserDto = new CreateUserDto();
                createUserDto.setEmail(newUser.getEmail());
                createUserDto.setPassword(generateRandomPassword());
                createUserDto.setFirstName(newUser.getFirstName());
                createUserDto.setUsername(newUser.getUserName());
                createUserDto.setLastName(newUser.getLastName());
                createUserDto.setTenantId(newUser.getTenantId());
                Optional<UserCategoryModel> categoryModel = userCategoryRepository.findByCategory("Student");
                if (categoryModel.isPresent()) {
                    createUserDto.setRole("Student");
                    user.setUserCategoryModel(categoryModel.get());
                }
                ResponseEntity<KeycloakResponseObject> response = keycloakServiceProxy.addUser(createUserDto);
                if (response.getBody().getCode() == 0) {
                    //res = new ResponseObject(ConstantUtil.ERROR_CODE, response.getBody().getMessage());
                } else {
                    log.info("kID" + response.getBody().getKID());
                    user.setKID(response.getBody().getKID());
                    BeanUtils.copyProperties(newUser, user);
                    user.setLastName(newUser.getLastName());
                    Optional<Tenant> optionalTenant = tenantRepository.findByTenantId(newUser.getTenantId());
                    if (optionalTenant.isPresent()) {
                        user.setTenantId(optionalTenant.get().getTenantId());
                    }
                    user = usersRepository.save(user);
                    email.setUserName(user.getUserName());
                    email.setTo(user.getEmail());
                    email.setFirstName(user.getFirstName());
                    email.setLastName(user.getLastName());
                    email.setPassword(createUserDto.getPassword());
                    System.out.println("added emails:::" + email.getTo());
                    userRegEmail.add(email);
                }
            } catch (Exception e) {
                log.error("Error {}", e);
            }
        }
        System.out.println("--------------------------");
        userRegEmail.forEach(System.out::println);
        System.out.println("--------------------------");
        ResponseEntity<ResponseObject> res = emailServiceProxy.sendRegistrationsEmail(userRegEmail);
        // List<RegistrationEmailStatusDto> emailStatusResponse = (List<RegistrationEmailStatusDto>) res.getBody().getData();

        List<RegistrationEmailStatusDto> emailStatusResponse = new ObjectMapper().convertValue(
                res.getBody().getData(),
                new TypeReference<>() {
                });
        emailStatusResponse.forEach(System.out::println);
        for (RegistrationEmailStatusDto emails : emailStatusResponse) {
            for (NewUsers userz : usersList) {
                if (userz.getEmail().equalsIgnoreCase(emails.getTo()))
                    userz.setEmailStatus(true);
                userz.setRegistrationStatus(true);
                newUsersRepository.save(userz);
            }
        }
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseObject getUserProfileDetailsByKid(String tenant, String headerUserId, String sub) {
        ResponseObject res;
        try {
            Optional<UsersModel> user = usersRepository.getBykID(sub);

            if (user.isEmpty()) {
                res = new ResponseObject(ConstantUtil.ERROR_CODE, "User Not Found!!!");
            } else {
                UserDTO dto = new UserDTO();
                dto.setFirstName(user.get().getFirstName());
                dto.setLastName(user.get().getLastName());
                dto.setId(user.get().getId());
                dto.setTenantId(user.get().getTenantId());
                dto.setCategory(user.get().getUserCategoryModel().getCategory());
                dto.setPhoto(imagePath + "/" + user.get().getPhoto());
                res = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Success", dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseObject(ConstantUtil.ERROR_CODE, "Error Occurred please contact your Administrator");
        }

        return res;
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$!()";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
}
