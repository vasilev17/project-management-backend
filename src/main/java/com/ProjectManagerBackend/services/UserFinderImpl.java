package com.ProjectManagerBackend.services;

import com.ProjectManagerBackend.config.JwtProvider;
import com.ProjectManagerBackend.models.User;
import com.ProjectManagerBackend.repositories.UserRepository;
import com.ProjectManagerBackend.services.interfaces.UserFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserFinderImpl implements UserFinder {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {

        String email = jwtProvider.getTokenEmail(jwt);

        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("user not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new Exception("user not found");
        }
        return optionalUser.get();
    }


}
