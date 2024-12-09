package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Repository
@RequiredArgsConstructor

public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveSchedule(Schedule schedule) {
        // DB에 생성한 일정 저장
        String sql = "INSERT INTO schedule(title, content, author_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, schedule.getTitle(), schedule.getContent(), schedule.getAuthorId());

    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(Long authorId, String updatedAt) {
        // DB의 일정 조회 로직 (전부, author 기준, 수정일 기준)
        StringBuilder sql = new StringBuilder(
                "SELECT s.id, a.user_id, a.nickname, s.title, s.content, s.updated_at " +
                        "FROM schedule s " +
                        "JOIN author a ON s.author_id = a.author_id");

        List<Object> params = new ArrayList<>();
        boolean hasCondition = false;

        if (authorId != null) {
            sql.append(" WHERE a.author_id = ?");
            params.add(authorId);
            hasCondition = true;
        }

        if (updatedAt != null) {
            if (hasCondition) {
                sql.append(" AND s.updated_at LIKE ?");
            } else {
                sql.append(" WHERE s.updated_at LIKE ?");
            }
            params.add(updatedAt + "%");
        }

        sql.append(" ORDER BY s.updated_at DESC");
        return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), params.toArray());
    }

    @Override
    public Long userIdToAuthorId(String userId) {
        if (userId == null) {
            return null;
        }
        String sql = "SELECT author_id FROM author WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class, userId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("해당 userId에 대한 authorId가 존재하지 않습니다.");
        }
    }

    @Override
    public Optional<ScheduleResponseDto> findScheduleById(Long id) {
        // DB 일정 단건 조회 (id 기반)
        List<ScheduleResponseDto> result = jdbcTemplate.query(
                "SELECT s.id, a.user_id, a.nickname, s.title, s.content, s.updated_at " +
                        "FROM schedule s " +
                        "JOIN author a ON s.author_id = a.author_id " +
                        "WHERE id = ? ",
                scheduleRowMapper(),
                id);

        return result.stream().findAny();
    }

    @Override
    public Long idToAuthorId(Long id) {
        String sql = "SELECT s.author_id FROM schedule s JOIN author a ON s.author_id = a.author_id WHERE s.id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, id);
    }

    @Override
    public void updateTitle(Long id, String title) {
        // DB title 수정(id 기반)
        String sql = "UPDATE schedule SET title = ? WHERE id = ? ";
        jdbcTemplate.update(sql, title, id);
    }

    @Override
    public void updateContent(Long id, String content) {
        // DB content 수정 (id 기반)
        String sql = "UPDATE schedule SET content = ? WHERE id = ? ";
        jdbcTemplate.update(sql, content, id);
    }

    @Override
    public boolean isValidId(Long id) {
        // 주어진 id가 DB에 존재하는지 확인
        String sql = "SELECT COUNT(id) FROM schedule WHERE id = ? ";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteSchedule(Long id) {
        // 주어진 id에 해당하는 일정을 DB 에서 삭제
        Long authorId = idToAuthorId(id);
        String sql = "DELETE FROM schedule WHERE author_id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        // ResultSet 에서 데이터를 추출하여 ScheduleResponseDto 로 변환
        // 각 필드를 매핑하여 Dto 객체 생성
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("user_id"),
                rs.getString("nickname"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
