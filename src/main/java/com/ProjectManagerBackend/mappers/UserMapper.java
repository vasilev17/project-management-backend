package com.ProjectManagerBackend.mappers;

import com.ProjectManagerBackend.dtos.RegistrationDTO;
import com.ProjectManagerBackend.models.User;


public interface UserMapper {

    User convertRegistrationDTOToUser(RegistrationDTO dto);

}
