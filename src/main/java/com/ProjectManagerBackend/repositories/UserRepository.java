package com.ProjectManagerBackend.repositories;

import com.ProjectManagerBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByEmail(String email);

}
