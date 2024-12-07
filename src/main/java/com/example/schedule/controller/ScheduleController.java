package com.example.schedule.controller;

import com.example.schedule.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor

public class ScheduleController {
    private final ScheduleService scheduleService;

}
