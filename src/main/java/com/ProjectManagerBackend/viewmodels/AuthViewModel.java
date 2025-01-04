package com.ProjectManagerBackend.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthViewModel {

    private String jwtToken;
    private String message;

}
