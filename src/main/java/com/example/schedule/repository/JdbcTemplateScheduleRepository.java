package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor

public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long saveSchedule(Schedule schedule) {
        // DB에 생성한 일정 저장
        String sql = "INSERT INTO schedule(author, password, title, content) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, schedule.getAuthor(), schedule.getPassword(), schedule.getTitle(), schedule.getContent());
        // INSERT 쿼리 후, LAST_INSERT_ID()를 통해 생성된 ID 반환
        String getIdSql = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(getIdSql, Long.class);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(String author, String updatedAt) {
        // DB의 일정 조회 로직 (전부, author 기준, 수정일 기준)
        StringBuilder sql = new StringBuilder("SELECT id, author, title, content, updated_at FROM schedule");
        List<Object> params = new ArrayList<>();
        boolean hasCondition = false;

        if(author != null){
            sql.append(" WHERE author = ?");
            params.add(author);
            hasCondition =true;
        }

        if(updatedAt != null){
            if(hasCondition){
                sql.append(" AND updated_at LIKE ?");
            } else {
                sql.append(" WHERE updated_at LIKE ?");
            }
            params.add(updatedAt + "%");
        }

        sql.append(" ORDER BY updated_at DESC");
        return jdbcTemplate.query(sql.toString(),scheduleRowMapper(), params.toArray());
    }

    @Override
    public Optional<ScheduleResponseDto> findScheduleById(Long id) {
        // DB 일정 단건 조회 (id 기반)
        List<ScheduleResponseDto> result = jdbcTemplate.query(
                "SELECT * FROM schedule WHERE id = ?",
                scheduleRowMapper(),
                id);

        return result.stream().findAny();
    }

    @Override
    public void updateAuthor(Long id, String author) {
        // DB author 수정(id 기반)
        String sql = "UPDATE schedule SET author = ? WHERE id = ? ";
        jdbcTemplate.update(sql, author, id);
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
    public boolean isValidPassword(Long id, String password) {
        // 주어진 id와 비밀번호가 일치하는지 확인
        String sql = "SELECT COUNT(id) FROM schedule WHERE id = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id, password);
        return count != null && count > 0;
    }

    @Override
    public void deleteSchedule(Long id) {
        // 주어진 id에 해당하는 일정을 DB 에서 삭제
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper(){
        // ResultSet 에서 데이터를 추출하여 ScheduleResponseDto 로 변환
        // 각 필드를 매핑하여 Dto 객체 생성
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("author"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
