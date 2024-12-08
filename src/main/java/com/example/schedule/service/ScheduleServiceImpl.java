package com.example.schedule.service;

import com.example.schedule.dto.*;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


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

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        return scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto requestDto) {

        validateId(id);
        validatePassword(id, requestDto.getPassword());

        if(requestDto.getAuthor() != null){
            updateAuthor(id, requestDto.getAuthor());
        }

        if(requestDto.getTitle() != null){
            updateTitle(id, requestDto.getTitle());
        }

        if(requestDto.getContent() != null){
            updateContent(id, requestDto.getContent());
        }

        Optional<ScheduleResponseDto> updatedSchedule = scheduleRepository.findScheduleById(id);

        return updatedSchedule
                .orElseThrow(() -> new NoSuchElementException("일치하는 일정이 없습니다."));
    }

    @Override
    public void deleteSchedule(Long id, DeleteScheduleRequestDto requestDto) {

        validateId(id);
        validatePassword(id, requestDto.getPassword());
        scheduleRepository.deleteSchedule(id);

    }

    private void validateId(Long id) {
        if (!scheduleRepository.isValidId(id)) {
            throw new NoSuchElementException("일치하는 ID가 없습니다.");
        }
    }

    private void validatePassword(Long id, String password) {
        if (!scheduleRepository.isValidPassword(id, password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void updateAuthor(Long id, String author){
        scheduleRepository.updateAuthor(id, author);
    }

    private void updateTitle(Long id, String title){
       scheduleRepository.updateTitle(id, title);
    }

    private void updateContent(Long id, String content){
        scheduleRepository.updateContent(id, content);
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
