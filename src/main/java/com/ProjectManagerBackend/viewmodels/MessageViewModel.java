package com.ProjectManagerBackend.viewmodels;

import com.ProjectManagerBackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageViewModel {
    private Long id;
    private String body;
    private LocalDateTime creationDate;
    private User author;

}
