package com.example.schedule.controller;

import com.example.schedule.dto.ApiResponseDto;
import com.example.schedule.dto.LoginRequestDto;
import com.example.schedule.dto.SignupRequestDto;
import com.example.schedule.service.AuthService;
import com.example.schedule.util.Session;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

@RequiredArgsConstructor

public class UserController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(
            @RequestBody SignupRequestDto requestDto) {
        authService.signup(requestDto);
        return ResponseEntity.ok(new ApiResponseDto("등록이 완료되었습니다.", true));
    }

    // 나중에 이넘 ??
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(
            @RequestBody LoginRequestDto requestDto, HttpSession httpSession) {
        boolean status = authService.login(requestDto, httpSession);
        if (status) {
            return ResponseEntity.ok(new ApiResponseDto("로그인 성공", true));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponseDto("로그인 실패", false));
        }// 로그인 실패의 이유를 알기 어렵다.
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpSession session) {
        Session.invalidSession(session);
        return ResponseEntity.ok(new ApiResponseDto("로그아웃 성공", true));
    }
}
