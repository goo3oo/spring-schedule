package com.example.schedule.repository;

import com.example.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
