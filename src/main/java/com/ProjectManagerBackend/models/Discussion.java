package com.ProjectManagerBackend.models;

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
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Project project;

    @ManyToMany
    private List<User> participants = new ArrayList<>();

    @OneToMany(mappedBy = "discussion", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Message> messages;

}
