package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class CreateScheduleResponseDto {
    // 일정 생성 응답 Dto
    private Long id;
    private String author;
    private String title;
    private String content;
}
