package com.ProjectManagerBackend.controllers;

import com.ProjectManagerBackend.common.constants.ApiMessageConstants;
import com.ProjectManagerBackend.common.constants.StatusMessageConstants;
import com.ProjectManagerBackend.dtos.LoginDTO;
import com.ProjectManagerBackend.dtos.RegistrationDTO;
import com.ProjectManagerBackend.services.interfaces.UserService;
import com.ProjectManagerBackend.viewmodels.AuthViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = ApiMessageConstants.USER_TAG_NAME, description = ApiMessageConstants.USER_TAG_DESCRIPTION)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = ApiMessageConstants.REGISTER)
    public ResponseEntity<AuthViewModel> register(@RequestBody RegistrationDTO registrationModel) throws Exception {

        String token = userService.register(registrationModel);

        AuthViewModel response = new AuthViewModel();
        response.setMessage(String.format(StatusMessageConstants.SUCCESSFUL, "Registration"));
        response.setJwtToken(token);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    @Operation(summary = ApiMessageConstants.LOGIN)
    public ResponseEntity<AuthViewModel> login(@RequestBody LoginDTO loginModel) {

        String token = userService.login(loginModel);

        AuthViewModel response = new AuthViewModel();
        response.setMessage(String.format(StatusMessageConstants.SUCCESSFUL, "Login"));
        response.setJwtToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
