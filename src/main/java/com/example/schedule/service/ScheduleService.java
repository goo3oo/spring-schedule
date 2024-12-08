package com.example.schedule.service;

import com.example.schedule.dto.*;

import java.util.List;

public interface ScheduleService {
    CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto);
    List<ScheduleResponseDto> findAllSchedule(String author, String updatedAt);
    ScheduleResponseDto findScheduleById(Long id);
    ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto requestDto);
    void deleteSchedule(Long id, DeleteScheduleRequestDto requestDto);
}
