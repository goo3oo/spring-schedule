package com.example.schedule.util;

import jakarta.servlet.http.HttpSession;

import java.util.NoSuchElementException;

public class Session {

    public static final String SESSION_AUTHOR_ID = "authorId";

    public static void setSessionAttribute(HttpSession session, Long value){
        session.setAttribute(SESSION_AUTHOR_ID, value);
    }

    public static Long getSessionAttribute(HttpSession session){
        if (session != null) {
            Object authorId = session.getAttribute(SESSION_AUTHOR_ID);
            if (authorId == null) {
                throw new NoSuchElementException("로그인이 필요합니다.");
            }
            return (Long) authorId;
        }
        throw new IllegalStateException("로그인 정보가 없습니다.");
    }

    public static void invalidSession(HttpSession session){
        session.invalidate();
    }
}
