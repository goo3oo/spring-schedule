package com.example.schedule.service;

import com.example.schedule.dto.LoginRequestDto;
import com.example.schedule.dto.SignupRequestDto;
import com.example.schedule.entity.Author;
import com.example.schedule.repository.AuthorRepository;
import com.example.schedule.util.Session;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    // 세션에 저장할 변수명??

    public void signup(SignupRequestDto signupRequestDto){
        // 가입 로직
        // 비밀번호 bcrypt 방식으로 인코딩
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        // 인코딩한 비밀번호로 Entity 생성 후, Entity 정보를 DB에 저장
        Author author = signupRequestDto.toEntity(encodedPassword);
        authorRepository.save(author);
    }

    public boolean login(LoginRequestDto loginRequestDto, HttpSession session){
        // 로그인 로직
        // 유저 아이디가 유효한지 검사
        Author author = isValidAuthor(loginRequestDto.getUserId());
        // 유저 비밀번호가 유효한지 검사
        if(!isValidPassword(loginRequestDto.getPassword(),author.getPassword())){
            return false;
        }
        // 세션에 유효한 정보 저장
        Session.setSessionAttribute(session, author.getId());

        return true;
        // 나중에 로그아웃 하기 전에 또 로그인했을 때 어떻게 되는지 ?? 확인해보기
        // 확인해보기 싫으면 검사하기
     }

    private Author isValidAuthor(String userId){
        // 유저 아이디 유효성 검사
        return authorRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 ID 입니다."));
    }

    private boolean isValidPassword(String inputPassword, String storedPassword) {
        // 유저 비밀번호 유효성 검사
        return passwordEncoder.matches(inputPassword, storedPassword);
    }
}
