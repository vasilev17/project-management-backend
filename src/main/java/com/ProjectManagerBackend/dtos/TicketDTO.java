package com.ProjectManagerBackend.dtos;

import com.ProjectManagerBackend.common.enums.Importance;
import com.ProjectManagerBackend.common.enums.Progress;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
public class TicketDTO {
    private String name;
    private String description;
    private LocalDate deadline;
    private Importance importance = Importance.Low;
    private Progress progress = Progress.ToDo;
    private List<String> tags = new ArrayList<>();
}
