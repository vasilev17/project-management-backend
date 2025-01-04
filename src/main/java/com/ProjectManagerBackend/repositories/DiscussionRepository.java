package com.ProjectManagerBackend.repositories;

import com.ProjectManagerBackend.models.Discussion;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
}
