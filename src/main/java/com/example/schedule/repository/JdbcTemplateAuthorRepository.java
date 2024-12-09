package com.example.schedule.repository;

import com.example.schedule.entity.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateAuthorRepository implements AuthorRepository {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public void save(Author author) {
        String sql = "INSERT INTO author (user_id, password, email, nickname) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, author.getUserId(), author.getPassword(), author.getEmail(), author.getNickname());
    }

    @Override
    public Optional<Author> findByUserId(String userId) {

        String sql = "SELECT author_id, user_id, password FROM author WHERE user_id = ?";

        if (userId == null) {
            sql = "SELECT author_id, user_id, password FROM author WHERE user_id IS NULL";
        }
        try{
            Author author = jdbcTemplate.queryForObject(sql, new Object[]{userId}, authorRowMapper());
            return Optional.ofNullable(author);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    private RowMapper<Author> authorRowMapper() {
        return (rs, rowNum) ->  Author.builder()
                .id(rs.getLong("author_id"))
                .userId(rs.getString("user_id"))
                .password(rs.getString("password"))
                .build();
    }
}
