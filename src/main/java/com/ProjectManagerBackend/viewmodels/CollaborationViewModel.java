package com.ProjectManagerBackend.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaborationViewModel {

    private String message;
    private String collabToken;

}
