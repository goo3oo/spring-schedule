package com.example.schedule.repository;

import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.entity.Schedule;

public interface ScheduleRepository {
    Long createSchedule(Schedule schedule);
}
