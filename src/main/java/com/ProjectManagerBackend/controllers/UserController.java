package com.ProjectManagerBackend.controllers;


import com.ProjectManagerBackend.config.JwtProvider;
import com.ProjectManagerBackend.dtos.LoginDTO;
import com.ProjectManagerBackend.dtos.RegistrationDTO;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.repositories.UserRepository;
import com.ProjectManagerBackend.services.UserService;
import com.ProjectManagerBackend.viewmodels.AuthViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<AuthViewModel> register(@RequestBody RegistrationDTO registrationModel) throws Exception {

        //Check if user exists
        User existingUser = userRepo.findByEmail(registrationModel.getEmail());

        if (existingUser != null)
            throw new Exception("User with this email already exists!");

        User newUser = new User();
        newUser.setEmail(registrationModel.getEmail());
        newUser.setPassword(passEncoder.encode(registrationModel.getPassword()));
        newUser.setName(registrationModel.getName());

        userRepo.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(registrationModel.getEmail(), registrationModel.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtProvider.generateJwtToken(auth);

        AuthViewModel response = new AuthViewModel();
        response.setMessage("Registration successful!");
        response.setJwtToken(token);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthViewModel> login(@RequestBody LoginDTO loginModel) {
        String username = loginModel.getEmail();
        String password = loginModel.getPassword();

        Authentication auth = validateCredentials(username, password);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtProvider.generateJwtToken(auth);

        AuthViewModel response = new AuthViewModel();
        response.setMessage("Login successful!");
        response.setJwtToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private Authentication validateCredentials(String username, String password) {

        //Check if user exists
        UserDetails existingUser = userService.loadUserByUsername(username);

        //Check if password matches
        boolean doesPassMatch = passEncoder.matches(password, existingUser.getPassword());

        if (existingUser == null || doesPassMatch == false)
            throw new BadCredentialsException("Incorrect username or password!");

        return new UsernamePasswordAuthenticationToken(existingUser, null, existingUser.getAuthorities());
    }

}
