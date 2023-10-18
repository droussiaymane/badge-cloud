package com.vizzibl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.vizzibl.dto.CreateUserDto;
import com.vizzibl.dto.UserPasswordResetDto;
import com.vizzibl.response.ConstantUtil;
import com.vizzibl.response.KeycloakResponseObject;
import com.vizzibl.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KeycloakAdminService {

    @Value("${keycloak.realm}")
    private String clientRealm;

    @Autowired
    private Keycloak keycloak;

    @Autowired
    private ObjectMapper objectMapper;

    public KeycloakResponseObject createUser(CreateUserDto createUserDto) throws JsonProcessingException {
        KeycloakResponseObject response = null;
        try {
            log.info("Entity :", clientRealm);
            // keycloak.tokenManager().getAccessToken();
            UsersResource usersResource = keycloak.realm(clientRealm).users();
            CredentialRepresentation passwordCredentials = new CredentialRepresentation();
            passwordCredentials.setTemporary(false);
            passwordCredentials.setType(CredentialRepresentation.PASSWORD);
            passwordCredentials.setValue(createUserDto.getPassword());

            Map<String, List<String>> attributes = new HashMap<>();

            attributes.put("tenantId", List.of(createUserDto.getTenantId()));
            UserRepresentation kcUser = new UserRepresentation();
            kcUser.setUsername(createUserDto.getUsername());
            kcUser.setCredentials(Collections.singletonList(passwordCredentials));
            kcUser.setFirstName(createUserDto.getFirstName());
            kcUser.setLastName(createUserDto.getLastName());
            kcUser.setEmail(createUserDto.getEmail());

            kcUser.setEnabled(true);
            kcUser.setEmailVerified(false);
            kcUser.setAttributes(attributes);
            javax.ws.rs.core.Response keycloakResponse = usersResource.create(kcUser);

            log.info("Entity : {} , reason : {} , meta : {}", keycloakResponse.getStatus(),
                    keycloakResponse.getStatusInfo().getReasonPhrase(), keycloakResponse.getMetadata());
            if (keycloakResponse.getStatus() == 201) {
                addRealmRoleToUser(createUserDto.getUsername(), createUserDto.getRole());
                String createdUserId = keycloakResponse.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
                UserRepresentation createdUser = keycloak.realm(clientRealm).users().get(createdUserId).toRepresentation();
                keycloak.realm(clientRealm).users().get(createdUser.getId()).sendVerifyEmail();
                response = new KeycloakResponseObject(ConstantUtil.SUCCESS_CODE, "success", createdUser.getId());
            } else {
                response = new KeycloakResponseObject(ConstantUtil.ERROR_CODE, keycloakResponse.getStatusInfo().getReasonPhrase());
            }
        } catch (Exception e) {
            log.error("Exception in createUser for request :{}", createUserDto, e);
            response = new KeycloakResponseObject(ConstantUtil.ERROR_CODE, "Internal Server Error");
        }
        return response;

    }

    public ResponseObject updateUser(CreateUserDto updateUserDto, String userId) throws JsonProcessingException {
        ResponseObject response = null;
        try {
            UsersResource usersResource = keycloak.realm(clientRealm).users();

            UserRepresentation kcUser = new UserRepresentation();
            if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().isEmpty()) {
                CredentialRepresentation passwordCredentials = new CredentialRepresentation();
                passwordCredentials.setTemporary(false);
                passwordCredentials.setType(CredentialRepresentation.PASSWORD);
                passwordCredentials.setValue(updateUserDto.getPassword());
                kcUser.setCredentials(Collections.singletonList(passwordCredentials));
            }
            if (updateUserDto.getTenantId() != null && !updateUserDto.getTenantId().isEmpty()) {
                Map<String, List<String>> attributes = new HashMap<>();
                attributes.put("tenantId", List.of(updateUserDto.getTenantId()));
                kcUser.setAttributes(attributes);
            }

            if (updateUserDto.getUsername() != null && !updateUserDto.getUsername().isEmpty()) {
                kcUser.setUsername(updateUserDto.getUsername());
            }
            if (updateUserDto.getFirstName() != null && !updateUserDto.getFirstName().isEmpty()) {
                kcUser.setFirstName(updateUserDto.getFirstName());
            }
            if (updateUserDto.getLastName() != null && !updateUserDto.getLastName().isEmpty()) {
                kcUser.setLastName(updateUserDto.getLastName());
            }
            if (updateUserDto.getEmail() != null && !updateUserDto.getEmail().isEmpty()) {
                kcUser.setEmail(updateUserDto.getEmail());
            }

            kcUser.setEnabled(true);
            kcUser.setEmailVerified(false);
            usersResource.get(userId).update(kcUser);

            response = new ResponseObject(ConstantUtil.SUCCESS_CODE, "success");
        } catch (Exception error) {
            log.error("Exception in createUser for request :{}", updateUserDto, userId, error);
            response = new ResponseObject(ConstantUtil.ERROR_CODE, "Internal Server Error" + error);
        }
        return response;
    }

    public ResponseObject userPasswordReset(UserPasswordResetDto updateUserDto, String userId)
            throws JsonProcessingException {
        ResponseObject response = null;
        try {
            UsersResource usersResource = keycloak.realm(clientRealm).users();
            CredentialRepresentation passwordCredentials = new CredentialRepresentation();
            passwordCredentials.setTemporary(false);
            passwordCredentials.setType(CredentialRepresentation.PASSWORD);
            passwordCredentials.setValue(updateUserDto.getPassword());
            UserRepresentation kcUser = new UserRepresentation();
            kcUser.setCredentials(Collections.singletonList(passwordCredentials));

            // kcUser.setGroups(Lists.newArrayList(createUserDto.getRole()));
            usersResource.get("" + userId).update(kcUser);

            response = new ResponseObject(ConstantUtil.SUCCESS_CODE, "User Updated");
        } catch (Exception error) {
            log.error("Exception in createUser for request :{}", updateUserDto, userId, error);
            response = new ResponseObject(ConstantUtil.SUCCESS_CODE, "Internal Server Error" + error);
        }
        return response;
    }

    public List<String> getAllRoles() {
        List<String> availableRoles = keycloak
                .realm(clientRealm)
                .roles()
                .list()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());
        return availableRoles;
    }

    public void addRealRole(String newRole) {
        if (!getAllRoles().contains(newRole)) {
            RoleRepresentation roleRepresentation = new RoleRepresentation();
            roleRepresentation.setName(newRole);
            roleRepresentation.setDescription("role_id" + newRole);
            keycloak.realm(clientRealm).roles().create(roleRepresentation);
        }
    }

    public void addRealmRoleToUser(String userName, String roleName) {
        String userId = keycloak
                .realm(clientRealm)
                .users()
                .search(userName)
                .get(0)
                .getId();

        UserResource kcUser = keycloak
                .realm(clientRealm)
                .users()
                .get(userId);

        List<RoleRepresentation> realmRoleToAdd = new ArrayList<>();
        realmRoleToAdd.add(keycloak
                .realm(clientRealm)
                .roles()
                .get(roleName)
                .toRepresentation());
        kcUser.roles().realmLevel().add(realmRoleToAdd);
    }

    /// Search User By Email OR Username
    public UserRepresentation getUserData(String name) {
        UserRepresentation userRepresentation = keycloak
                .realm(clientRealm)
                .users()
                .search(name)
                .get(0);
        userRepresentation.getEmail();
        userRepresentation.getId();
        userRepresentation.getFirstName();
        userRepresentation.getLastName();
        userRepresentation.getCreatedTimestamp();
        userRepresentation.getRealmRoles();
        userRepresentation.getAccess();

        return userRepresentation;
    }

    public void bulkCreateUsers(MultipartFile file) throws Exception {
        CreateUserDto cUser;
        Reader reader = new InputStreamReader(file.getInputStream());
        try (CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {
            List<String[]> rows = csvReader.readAll();
            for (String[] row : rows) {
                cUser = new CreateUserDto();
                cUser.setFirstName(row[0]);
                cUser.setLastName(row[1]);
                cUser.setUsername(row[2]);
                cUser.setEmail(row[3]);
                String password = generateRandomPassword(10);
                cUser.setPassword(password);
                cUser.setRole(row[4]);
                cUser.setTenantId(row[5]);
                createUser(cUser);
            }
        }
    }

    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$!()";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    public boolean sendVerificationEmail(String username) {
        try {
            List<UserRepresentation> userRepresentations = keycloak.realm(clientRealm).users().searchByUsername(username, true);
            if (userRepresentations != null && userRepresentations.size() > 0) {
                UserRepresentation user = userRepresentations.get(0);
                if (!user.isEmailVerified()) {
                    keycloak.realm(clientRealm).users().get(userRepresentations.get(0).getId()).sendVerifyEmail();
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            log.error("Error ", e);
        }
        return false;
    }
}
