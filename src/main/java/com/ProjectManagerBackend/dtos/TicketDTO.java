package com.ProjectManagerBackend.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
public class TicketDTO {
    private String name;
    private String description;
    private LocalDate deadline;
    private String importance;
    private String progress;
    private List<String> tags = new ArrayList<>();
}
