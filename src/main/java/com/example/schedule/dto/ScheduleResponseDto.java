package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor

public class ScheduleResponseDto {
    // 일정 응답 Dto
    private Long id;
    private String userId;
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime updatedAt;

}
