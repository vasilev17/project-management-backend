package com.ProjectManagerBackend.models;

import com.ProjectManagerBackend.common.enums.DevelopmentScope;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private List<String> tags = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private DevelopmentScope developmentScope;

    @ManyToOne
    private User creator;

    @ManyToMany
    private List<User> team = new ArrayList<>();

    @OneToMany(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Discussion discussion;

}
