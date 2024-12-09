package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Author {
    private Long id;
    private String userId;
    private String password;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

