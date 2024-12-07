package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class CreateScheduleResponseDto {
    private Long id;
    private String author;
    private String title;
    private String content;
}
