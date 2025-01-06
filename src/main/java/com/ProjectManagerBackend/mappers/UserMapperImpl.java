package com.ProjectManagerBackend.mappers;

import com.ProjectManagerBackend.dtos.RegistrationDTO;
import com.ProjectManagerBackend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private PasswordEncoder passEncoder;

    @Override
    public User convertRegistrationDTOToUser(RegistrationDTO dto) {

        if (dto == null)
            return null;

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPassword(passEncoder.encode(dto.getPassword()));

        return user;
    }
}
