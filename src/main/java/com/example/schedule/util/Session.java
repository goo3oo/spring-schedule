package com.example.schedule.util;

import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import java.util.NoSuchElementException;

@NoArgsConstructor

public class Session {

    public static final String SESSION_KEY = "authorId";

    public static void setSessionAttribute(HttpSession session, Long value) {
        if (session.getAttribute(SESSION_KEY) != null) {
            invalidSession(session);
        }
        session.setAttribute(SESSION_KEY, value);
    }

    public static Long getSessionAttribute(HttpSession session) {
        if (session != null) {
            Object sessionKey = session.getAttribute(SESSION_KEY);
            if (sessionKey == null) {
                throw new NoSuchElementException("로그인이 필요합니다.");
            }
            return (Long) sessionKey;
        }
        throw new IllegalStateException("로그인 정보가 없습니다.");
    }

    public static void invalidSession(HttpSession session) {
        session.invalidate();
    }
}
