package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor

public class Schedule {
    private Long id;
    private String author;
    private String password;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule (String author, String password, String title, String content){
        this.author = author;
        this.password = password;
        this.title = title;
        this.content = content;
    }
}
