package com.ProjectManagerBackend.viewmodels;

import com.ProjectManagerBackend.models.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentViewModel {
    private Long id;
    private User author;
    private String body;
    private LocalDateTime creationDate;
}
