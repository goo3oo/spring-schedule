package com.example.schedule.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

@Getter

public class UpdateScheduleRequestDto {
    // 일정 수정 요청 Dto
    private String nickname;
    @NotBlank(message = "제목을 입력해주세요.")
    // title 공백 금지
    private String title;
    private String content;
}
