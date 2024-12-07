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
    public Long createSchedule(Schedule schedule) {

        String sql = "INSERT INTO schedule(author, password, title, content) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, schedule.getAuthor(), schedule.getPassword(), schedule.getTitle(), schedule.getContent());

        String getIdSql = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(getIdSql, Long.class);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(String author, String updatedAt) {
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
        List<ScheduleResponseDto> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", scheduleRowMapper(), id);
        return result.stream().findAny();
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper(){
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("author"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
