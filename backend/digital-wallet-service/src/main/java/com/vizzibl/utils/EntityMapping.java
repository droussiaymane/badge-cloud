package com.vizzibl.utils;

import com.vizzibl.dto.UserDTO;
import com.vizzibl.entity.UsersModel;
import org.springframework.beans.BeanUtils;

public class EntityMapping {

    public static UserDTO EntityToDto(UsersModel usersModel) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(usersModel, dto);
        return dto;
    }
}
