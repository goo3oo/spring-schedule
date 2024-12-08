package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.Getter;

@Getter

public class CreateScheduleRequestDto {
    // 일정 생성 요청 Dto
    private String author;
    private String password;
    private String title;
    private String content;

    public Schedule toEntity(){
    // Dto -> Entity 변환
        return new Schedule(author, password, title, content);
    }
}
