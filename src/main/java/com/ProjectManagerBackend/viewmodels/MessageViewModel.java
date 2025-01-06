package com.ProjectManagerBackend.viewmodels;

import com.ProjectManagerBackend.models.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageViewModel {
    private Long id;
    private String body;
    private LocalDateTime creationDate;
    private User author;

}
