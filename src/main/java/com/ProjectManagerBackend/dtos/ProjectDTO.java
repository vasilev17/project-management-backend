package com.ProjectManagerBackend.dtos;

import com.ProjectManagerBackend.common.enums.DevelopmentScope;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDTO {
    private String name;
    private String description;
    private List<String> tags = new ArrayList<>();
    private DevelopmentScope developmentScope;
}
