package com.example.schedule.controller;


import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor

public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
            @RequestBody CreateScheduleRequestDto requestDto){
        return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.OK);
    }
}
