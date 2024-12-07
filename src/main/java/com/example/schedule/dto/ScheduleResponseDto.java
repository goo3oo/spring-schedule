package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor

public class ScheduleResponseDto {
    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime updatedAt;

}
