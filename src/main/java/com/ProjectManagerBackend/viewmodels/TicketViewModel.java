package com.ProjectManagerBackend.viewmodels;

import com.ProjectManagerBackend.common.enums.Importance;
import com.ProjectManagerBackend.common.enums.Progress;
import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class TicketViewModel {

    private Long id;
    private String description;
    private String name;
    private List<String> tags = new ArrayList<>();
    private LocalDate deadline;
    private Progress progress;
    private Importance importance;
    private User assignee;
    private User author;
    private Project project;

}
