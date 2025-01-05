package com.ProjectManagerBackend.viewmodels;

import com.ProjectManagerBackend.models.Project;
import com.ProjectManagerBackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketViewModel {

    private Long id;
    private String description;
    private String name;
    private Long projectID;
    private List<String> tags = new ArrayList<>();
    private LocalDate deadline;
    private String progress;
    private String importance;
    private User assignee;
    private User author;
    private Project project;

}
