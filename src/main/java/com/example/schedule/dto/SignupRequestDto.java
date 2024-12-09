package com.example.schedule.dto;

import com.example.schedule.entity.Author;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String userId;
    private String password;
    private String email;
    private String nickname;

    public Author toEntity(String encodedPassword){
        // Dto -> Entity 변환
        return Author.builder()
                .userId(this.userId)
                .password(encodedPassword)
                .email(this.email)
                .nickname(this.nickname)
                .build();
    }
}
