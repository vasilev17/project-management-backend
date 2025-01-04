package com.ProjectManagerBackend.repositories;

import com.ProjectManagerBackend.models.CollaborationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaborationRequestRepository extends JpaRepository<CollaborationRequest, Long> {

    CollaborationRequest findByToken(String token);
}
