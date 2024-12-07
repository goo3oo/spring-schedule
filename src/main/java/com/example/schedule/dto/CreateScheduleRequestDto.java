package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.Getter;

@Getter

public class CreateScheduleRequestDto {
    private String author;
    private String password;
    private String title;
    private String content;

    public Schedule toEntity(){
        return new Schedule(author, password, title, content);
    }
}
