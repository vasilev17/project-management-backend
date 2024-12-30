package com.ProjectManagerBackend.dtos;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String email;
    private String password;
    private String name;
}
