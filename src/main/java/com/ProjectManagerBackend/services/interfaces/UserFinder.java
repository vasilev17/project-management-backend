package com.ProjectManagerBackend.services.interfaces;

import com.ProjectManagerBackend.models.User;

public interface UserFinder {

    User findUserProfileByJwt(String jwt)throws Exception;

    User findUserByEmail(String email)throws Exception;

    User findUserById(Long userId)throws Exception;



}
