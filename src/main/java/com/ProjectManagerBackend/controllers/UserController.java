package com.ProjectManagerBackend.controllers;


import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.repositories.UserRepository;
import com.ProjectManagerBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws Exception {

        //Check if user exists
        User existingUser = userRepo.getByEmail(user.getEmail());

        if (existingUser != null)
            throw new Exception("User with this email already exists!");

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passEncoder.encode(user.getPassword()));
        newUser.setName(user.getName());

        return new ResponseEntity<>(userRepo.save(newUser), HttpStatus.CREATED);

    }


}
