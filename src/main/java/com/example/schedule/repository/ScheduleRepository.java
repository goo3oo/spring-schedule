package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.dto.UpdateScheduleRequestDto;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Long createSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedule(String author, String updatedAt);
    Optional<ScheduleResponseDto> findScheduleById(Long id);
    void updateAuthor(Long id, String author);
    void updateTitle(Long id, String title);
    void updateContent(Long id, String content);
    boolean isValidId(Long id);
    boolean isValidPassword(Long id, String password);
}
