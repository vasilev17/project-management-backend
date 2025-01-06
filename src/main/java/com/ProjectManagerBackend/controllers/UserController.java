package com.ProjectManagerBackend.controllers;

import com.ProjectManagerBackend.dtos.LoginDTO;
import com.ProjectManagerBackend.dtos.RegistrationDTO;
import com.ProjectManagerBackend.services.interfaces.UserService;
import com.ProjectManagerBackend.viewmodels.AuthViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthViewModel> register(@RequestBody RegistrationDTO registrationModel) throws Exception {

        String token = userService.register(registrationModel);

        AuthViewModel response = new AuthViewModel();
        response.setMessage("Registration successful!");
        response.setJwtToken(token);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthViewModel> login(@RequestBody LoginDTO loginModel) {

        String token = userService.login(loginModel);

        AuthViewModel response = new AuthViewModel();
        response.setMessage("Login successful!");
        response.setJwtToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
