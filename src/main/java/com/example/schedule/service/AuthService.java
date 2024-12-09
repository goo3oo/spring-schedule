package com.example.schedule.service;

import com.example.schedule.dto.LoginRequestDto;
import com.example.schedule.dto.SignupRequestDto;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    void signup(SignupRequestDto signupRequestDto);
    boolean login(LoginRequestDto loginRequestDto, HttpSession session);

}
