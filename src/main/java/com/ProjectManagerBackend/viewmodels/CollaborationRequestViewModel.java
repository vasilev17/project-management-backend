package com.ProjectManagerBackend.viewmodels;

import com.ProjectManagerBackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaborationRequestViewModel {

    private String message;
    private Long projectId;
    private User collabRequestCreator;


}
