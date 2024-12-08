package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.dto.UpdateScheduleRequestDto;

import java.util.List;

public interface ScheduleService {
    CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto);
    List<ScheduleResponseDto> findAllSchedule(String author, String updatedAt);
    ScheduleResponseDto findScheduleById(Long id);
    ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto requestDto);
}
