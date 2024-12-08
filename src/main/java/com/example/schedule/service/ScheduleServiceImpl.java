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
    public CreateScheduleResponseDto createSchedule(CreateScheduleRequestDto requestDto) {
        // 일정 생성 로직
        // 요청 DTO 를 Entity 로 변환하여 일정 생성
        Schedule schedule = requestDto.toEntity();
        Long id = scheduleRepository.saveSchedule(schedule);
        // 일정 저장 로직 실행 후, 응답 DTO 반한
        return new CreateScheduleResponseDto(id, schedule.getAuthor(), schedule.getTitle(), schedule.getContent());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(String author, String updatedAt) {
        // 일정 목록 조회 로직
        // 공백으로 입력된 author 나 updatedAt 값을 null 로 변환하여 검색 시 필터링 처리
        author = convertEmptyToNull(author);
        updatedAt = convertEmptyToNull(updatedAt);
        // 입력된 날짜의 형태 검증
        updatedAt = validateAndFormatDate(updatedAt);
        return scheduleRepository.findAllSchedule(author, updatedAt);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        // ID에 해당하는 일정 단건을 조회하는 로직
        // 일정이 없을 경우 NoSuchElementException 발생
        return scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto requestDto) {
        // 일정 수정 로직
        // author, title, content 값이 있을 경우 각각 수정

        // id 유효성 검사
        validateId(id);
        // password 유효성 검사
        validatePassword(id, requestDto.getPassword());
        // author 값 입력 시 수정 로직
        if(requestDto.getAuthor() != null){
            updateAuthor(id, requestDto.getAuthor());
        }
        // title 값 입력 시 수정 로직
        if(requestDto.getTitle() != null){
            updateTitle(id, requestDto.getTitle());
        }
        // content 값 입력 시 수정 로직
        if(requestDto.getContent() != null){
            updateContent(id, requestDto.getContent());
        }
        // 수정된 데이터 Dto 로 받아오기
        Optional<ScheduleResponseDto> updatedSchedule = scheduleRepository.findScheduleById(id);

        return updatedSchedule
                .orElseThrow(() -> new NoSuchElementException("일치하는 일정이 없습니다."));
    }

    @Override
    public void deleteSchedule(Long id, DeleteScheduleRequestDto requestDto) {
        // ID에 해당하는 일정 삭제
        // 유효성 검사 후 삭제 수행

        // 주어진 ID가 존재하는지 확인
        // ID가 없으면 NoSuchElementException 발생
        validateId(id);

        // 주어진 ID와 비밀번호가 일치하는지 확인
        // 비밀번호가 일치하지 않으면 IllegalArgumentException 발생
        validatePassword(id, requestDto.getPassword());

        // 일정 삭제 로직 실행
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
        // 작성자 수정
        scheduleRepository.updateAuthor(id, author);
    }

    private void updateTitle(Long id, String title){
        // 제목 수정
       scheduleRepository.updateTitle(id, title);
    }

    private void updateContent(Long id, String content){
        // 내용 수정
        scheduleRepository.updateContent(id, content);
    }

    private String convertEmptyToNull(String value) {
        // 입력값이 빈 문자열("")일 경우 null 로 변환
        return (value != null && value.isEmpty()) ? null : value;
    }

    private String validateAndFormatDate(String updatedAt) {
        // 입력된 날짜 형식이 "yyyy-MM-dd" 형식인지 확인하고,
        // 올바르지 않으면 IllegalArgumentException 발생
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
