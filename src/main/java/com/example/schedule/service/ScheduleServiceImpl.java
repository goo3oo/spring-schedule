package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Override
    public CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto) {
        Schedule schedule = requestDto.toEntity();
        Long id = scheduleRepository.createSchedule(schedule);
        return new CreateScheduleResponseDto(id, schedule.getAuthor(),schedule.getTitle(),schedule.getContent());
    }
}
