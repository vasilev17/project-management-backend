package com.ProjectManagerBackend.viewmodels;

import com.ProjectManagerBackend.models.Ticket;
import com.ProjectManagerBackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentViewModel {
    private Long id;
    private User author;
    private String body;
    private LocalDateTime creationDate;
}
