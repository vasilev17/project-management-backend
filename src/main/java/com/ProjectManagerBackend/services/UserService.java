package com.ProjectManagerBackend.services;

import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.getByEmail(username);

        if (user == null)
            throw new UsernameNotFoundException("User \"" + username + "\" not found!");

        List<GrantedAuthority> authorities = new ArrayList<>();

        UserDetails details = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

        return details;
    }
}
