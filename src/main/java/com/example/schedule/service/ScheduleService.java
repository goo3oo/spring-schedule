package com.example.schedule.service;

import com.example.schedule.dto.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface ScheduleService {
    void createSchedule(CreateScheduleRequestDto requestDto, Long authorId);
    List<ScheduleResponseDto> findAllSchedule(String userId, String updatedAt);
    ScheduleResponseDto findScheduleById(Long id);
    void updateSchedule(Long userId, UpdateScheduleRequestDto requestDto, Long authorId);
    void deleteSchedule(Long id, Long authorId);
}
