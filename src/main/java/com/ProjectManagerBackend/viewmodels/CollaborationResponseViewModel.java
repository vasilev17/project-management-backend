package com.ProjectManagerBackend.viewmodels;

import com.ProjectManagerBackend.models.User;
import lombok.Data;

@Data
public class CollaborationResponseViewModel {

    private String message;
    private Long projectId;
    private User collabRequestCreator;


}
