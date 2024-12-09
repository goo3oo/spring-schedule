package com.example.schedule.repository;

import com.example.schedule.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    void save(Author author);
    Optional<Author> findByUserId(String userId);
}
