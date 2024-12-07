package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.CreateScheduleResponseDto;

public interface ScheduleService {
    CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto);
}
