package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor

public class Schedule {
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule (String title, String content, Long authorId){
        // Dto -> Entity 변환 과정에서 사용
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }
}
