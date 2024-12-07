package com.example.schedule.service;

import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

}
