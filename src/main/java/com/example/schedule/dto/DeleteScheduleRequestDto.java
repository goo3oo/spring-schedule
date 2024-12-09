package com.example.schedule.dto;

import lombok.Getter;

@Getter
// 세션으로 확인하니까 필요업을듯?
public class DeleteScheduleRequestDto {
    // 일정 삭제 요청 Dto
    private String password;
}
