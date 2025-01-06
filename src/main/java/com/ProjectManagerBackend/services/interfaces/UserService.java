package com.ProjectManagerBackend.services.interfaces;

import com.ProjectManagerBackend.dtos.LoginDTO;
import com.ProjectManagerBackend.dtos.RegistrationDTO;
import com.ProjectManagerBackend.models.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    String register(RegistrationDTO registrationModel) throws Exception;

    String login(LoginDTO loginModel) throws BadCredentialsException;

    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(Long userId) throws Exception;

}
