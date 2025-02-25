package com.ProjectManagerBackend.models;

import com.ProjectManagerBackend.common.enums.Importance;
import com.ProjectManagerBackend.common.enums.Progress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User assignee;

    @ManyToOne
    private User author;

    @JsonIgnore
    @ManyToOne
    private Project project;

    private String name;
    private Long projectID;
    private String description;
    private LocalDate deadline;
    private List<String> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @Enumerated(EnumType.STRING)
    private Progress progress;

    @JsonIgnore
    @OneToMany(mappedBy = "ticket", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

}
