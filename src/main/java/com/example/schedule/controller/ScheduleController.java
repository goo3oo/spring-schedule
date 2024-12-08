package com.example.schedule.controller;

import com.example.schedule.dto.*;
import com.example.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor

public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
            @RequestBody CreateScheduleRequestDto requestDto) {
        // 요청받은 데이터로 일정 생성, 반환
        return new ResponseEntity<>(scheduleService.createSchedule(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String updatedAt) {
        // 일정 목록 조회, 반환
        return new ResponseEntity<>(scheduleService.findAllSchedule(author, updatedAt), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable Long id) {
        // 일정 조회 (id 기반), 반환
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody @Valid UpdateScheduleRequestDto requestDto) {
        // 일정 수정 (id 기반), 반환
        return new ResponseEntity<>(scheduleService.updateSchedule(id, requestDto),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody DeleteScheduleRequestDto requestDto) {
        // 일정 삭제(id 기반), 반환
        scheduleService.deleteSchedule(id, requestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
