package com.example.schedule.controller;

import com.example.schedule.dto.*;
import com.example.schedule.exception.UnauthorizedAccessException;
import com.example.schedule.service.ScheduleService;
import com.example.schedule.util.Session;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor

public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/add-schedule")
    public ResponseEntity<ApiResponseDto> createSchedule(
            @RequestBody CreateScheduleRequestDto requestDto, HttpSession session) {
            try{
                Long authorId = Session.getSessionAttribute(session);
                scheduleService.createSchedule(requestDto, authorId);
                return new ResponseEntity<>(new ApiResponseDto("일정이 등록되었습니다.", true), HttpStatus.CREATED);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<>(new ApiResponseDto("로그인이 필요합니다.", false), HttpStatus.UNAUTHORIZED);
            } catch (IllegalStateException e) {
                return new ResponseEntity<>(new ApiResponseDto("회원 정보가 없습니다.", false), HttpStatus.BAD_REQUEST);
            }
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String updatedAt) {
        // 일정 목록 조회, 반환
        return new ResponseEntity<>(scheduleService.findAllSchedule(userId, updatedAt), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable Long id) {
        // 일정 조회 (id 기반), 반환
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody @Valid UpdateScheduleRequestDto requestDto,
            HttpSession session) {
        // 일정 수정 (id 기반), 반환
        try {
            Long authorId = Session.getSessionAttribute(session);
            scheduleService.updateSchedule(id, requestDto, authorId);
            return new ResponseEntity<>(new ApiResponseDto("일정이 수정되었습니다.", true), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ApiResponseDto("로그인이 필요합니다.", false), HttpStatus.UNAUTHORIZED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ApiResponseDto("회원 정보가 없습니다.", false), HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedAccessException e) {
            return new ResponseEntity<>(new ApiResponseDto("수정 권한이 없습니다.", false), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto> deleteSchedule(
            @PathVariable Long id,
            HttpSession session) {
        // 일정 삭제(id 기반), 반환
        try {
            Long authorId = Session.getSessionAttribute(session);
            scheduleService.deleteSchedule(id, authorId);
            return new ResponseEntity<>(new ApiResponseDto("일정이 삭제되었습니다.", true), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ApiResponseDto("로그인이 필요합니다.", false), HttpStatus.UNAUTHORIZED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ApiResponseDto("회원 정보가 없습니다.", false), HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedAccessException e) {
            return new ResponseEntity<>(new ApiResponseDto("삭제 권한이 없습니다.", false), HttpStatus.FORBIDDEN);
        }
    }
}
