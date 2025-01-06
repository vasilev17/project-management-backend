package com.ProjectManagerBackend.services;

import com.ProjectManagerBackend.config.JwtProvider;
import com.ProjectManagerBackend.dtos.LoginDTO;
import com.ProjectManagerBackend.dtos.RegistrationDTO;
import com.ProjectManagerBackend.mappers.UserMapper;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.repositories.UserRepository;
import com.ProjectManagerBackend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String register(RegistrationDTO registrationModel) throws Exception {

        //Check if user exists
        User existingUser = userRepo.findByEmail(registrationModel.getEmail());

        if (existingUser != null)
            throw new Exception("User with this email already exists!");

        User newUser = userMapper.convertRegistrationDTOToUser(registrationModel);
        userRepo.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(registrationModel.getEmail(), registrationModel.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtProvider.generateJwtToken(auth);

        return token;
    }

    @Override
    public String login(LoginDTO loginModel) throws BadCredentialsException {

        Authentication auth = validateCredentials(loginModel);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtProvider.generateJwtToken(auth);

        return token;
    }

    private Authentication validateCredentials(LoginDTO credentials) throws BadCredentialsException {

        //Check if user exists
        UserDetails existingUser = loadUserByEmail(credentials.getEmail());

        //Check if password matches
        boolean doesPassMatch = passEncoder.matches(credentials.getPassword(), existingUser.getPassword());

        if (existingUser == null || doesPassMatch == false)
            throw new BadCredentialsException("Incorrect username or password!");

        return new UsernamePasswordAuthenticationToken(existingUser, null, existingUser.getAuthorities());
    }

    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("User \"" + email + "\" not found!");

        List<GrantedAuthority> authorities = new ArrayList<>();

        UserDetails details = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

        return details;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {

        String email = jwtProvider.getTokenEmail(jwt);

        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found!");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {

        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new Exception("User not found!");
        }
        return optionalUser.get();
    }

}
