package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    void saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedule(Long authorId, String updatedAt);
    Long userIdToAuthorId(String userId);
    Optional<ScheduleResponseDto> findScheduleById(Long id);
    void updateTitle(Long id, String title);
    void updateContent(Long id, String content);
    boolean isValidId(Long id);
    void deleteSchedule(Long id);
    Long idToAuthorId(Long id);
}
