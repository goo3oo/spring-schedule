package com.example.schedule.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

@Getter

public class UpdateScheduleRequestDto {
    private String author;
    private String password;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String content;
}
