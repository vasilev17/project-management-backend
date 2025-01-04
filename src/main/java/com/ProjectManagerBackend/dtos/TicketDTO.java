package com.ProjectManagerBackend.dtos;

import lombok.Data;

import java.time.LocalDate;


@Data
public class TicketDTO {
    private String name;
    private String description;
    private LocalDate deadline;
    private String importance;
    private String progress;
    private Long projectId;
}
