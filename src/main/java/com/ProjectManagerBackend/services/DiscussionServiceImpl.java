package com.ProjectManagerBackend.services;

import com.ProjectManagerBackend.models.Discussion;
import com.ProjectManagerBackend.repositories.DiscussionRepository;
import com.ProjectManagerBackend.services.interfaces.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscussionServiceImpl implements DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepo;

    @Override
    public Discussion createDiscussion(Discussion discussion) {
        return discussionRepo.save(discussion);
    }
}
