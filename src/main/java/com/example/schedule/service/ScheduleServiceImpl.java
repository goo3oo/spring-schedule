package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Override
    public CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto) {
        Schedule schedule = requestDto.toEntity();
        Long id = scheduleRepository.createSchedule(schedule);
        return new CreateScheduleResponseDto(id, schedule.getAuthor(), schedule.getTitle(), schedule.getContent());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(String author, String updatedAt) {
        author = convertEmptyToNull(author);
        updatedAt = convertEmptyToNull(updatedAt);
        updatedAt = validateAndFormatDate(updatedAt);
        return scheduleRepository.findAllSchedule(author, updatedAt);
    }

    private String convertEmptyToNull(String value) {
        return (value != null && value.isEmpty()) ? null : value;
    }

    private String validateAndFormatDate(String updatedAt) {
        if (updatedAt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate.parse(updatedAt, formatter);
                return updatedAt;
            } catch (DateTimeException e) {
                throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다. yyyy-MM-dd 형식으로 입력해주세요.");
            }
        }
        return null;
    }
}
