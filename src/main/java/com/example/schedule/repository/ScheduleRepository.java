package com.example.schedule.repository;

import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    Long createSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedule(String author, String updatedAt);
}
