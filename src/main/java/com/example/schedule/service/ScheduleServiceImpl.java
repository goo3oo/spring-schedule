package com.example.schedule.service;

import com.example.schedule.dto.*;
import com.example.schedule.entity.Schedule;
import com.example.schedule.exception.UnauthorizedAccessException;
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

@Service
@RequiredArgsConstructor

public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Override
    public void createSchedule(CreateScheduleRequestDto requestDto, Long authorId) {
        // 요청 DTO 를 Entity 로 변환하여 일정 생성
        Schedule schedule = requestDto.toEntity(authorId);
        scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(String userId, String updatedAt) {
        // 일정 목록 조회 로직
        // 공백으로 입력된 author 나 updatedAt 값을 null 로 변환하여 검색 시 필터링 처리
        userId = convertEmptyToNull(userId);
        updatedAt = convertEmptyToNull(updatedAt);

        Long authorId = scheduleRepository.userIdToAuthorId(userId);
        // 입력된 날짜의 형태 검증
        updatedAt = validateAndFormatDate(updatedAt);
        return scheduleRepository.findAllSchedule(authorId, updatedAt);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        // ID에 해당하는 일정 단건을 조회하는 로직
        // 일정이 없을 경우 NoSuchElementException 발생
        return scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void updateSchedule(Long id, UpdateScheduleRequestDto requestDto, Long sessionAuthorId) {

        if (isAuthorized(id, sessionAuthorId)) {
            throw new UnauthorizedAccessException("수정 권한이 없습니다.");
        }

        validateId(id);

        // author 값 입력 시 수정 로직
        // title 값 입력 시 수정 로직
        if(requestDto.getTitle() != null){
            updateTitle(id, requestDto.getTitle());
        }
        // content 값 입력 시 수정 로직
        if(requestDto.getContent() != null){
            updateContent(id, requestDto.getContent());
        }
    }

    @Override
    public void deleteSchedule(Long id, Long sessionAuthorId) {

        if (isAuthorized(id, sessionAuthorId)) {
            throw new UnauthorizedAccessException("수정 권한이 없습니다.");
        }
        // ID에 해당하는 일정 삭제
        // 유효성 검사 후 삭제 수행
        // 주어진 ID가 존재하는지 확인
        // ID가 없으면 NoSuchElementException 발생
        validateId(id);

        // 일정 삭제 로직 실행
        scheduleRepository.deleteSchedule(id);
    }

    private void validateId(Long id) {
        if (!scheduleRepository.isValidId(id)) {
            throw new NoSuchElementException(id+"번 게시물이 없습니다.");
        }
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

    private boolean isAuthorized(Long id, Long sessionAuthorId) {
        // 게시물 번호의 author id 조회하기
        Long authorId = scheduleRepository.idToAuthorId(id);  // DB 조회

        // 로그인한 사용자와 일정 작성자 비교
        return !authorId.equals(sessionAuthorId);
    }
}
