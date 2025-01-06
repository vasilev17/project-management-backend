package com.ProjectManagerBackend.viewmodels;

import com.ProjectManagerBackend.common.enums.DevelopmentScope;
import com.ProjectManagerBackend.models.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectViewModel {

    private Long id;
    private String name;
    private String description;
    private DevelopmentScope developmentScope;
    private List<String> tags = new ArrayList<>();
    private User creator;

}
