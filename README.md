# 🗓️ Schduel 만들기

## 🍪프로젝트 소개
이 프로젝트는 **Spring**을 이용한 일정 관리 애플리케이션입니다.    
사용자는 개인 일정을 **생성, 수정, 삭제, 조회**할 수 있으며, **로그인 기능**을 통해 각자의 일정을 관리할 수 있습니다.   
로그인 후, 일정의 생성, 수정, 삭제가 가능하며, 인증된 사용자만 접근할 수 있도록 보안 기능을 구현했습니다.




## 🔗 목차
1. [API 문서](#-api-문서)
    1. [일정 관련 API](#일정-관련-api)
        1. [일정 생성 API](#1-일정-생성-api)
        2. [일정 전체 조회](#2-일정-전체-조회)
        3. [일정 단건 조회](#3-일정-단건-조회)
        4. [일정 수정](#4-일정-수정)
        5. [일정 삭제](#5-일정-삭제)
    2. [인증 관련 API](#인증-관련-api)
        1. [회원가입](#1-회원가입)
        2. [로그인](#2-로그인)
        3. [로그아웃](#3-로그아웃)
2. [ERD 다이어그램](#erd-다이어그램)

## 📜 API 문서

## 일정 관련 API

| 기능       | Method  | 엔드포인트           |
|----------|---------|-----------------|
| 일정 생성    | `POST`    | `/api/schedule`   |
| 일정 전체 조회 | `GET`     | `/schedule`       |
| 일정 단건 조회 | `GET`     | `/schedule/{id}`  |
| 일정 수정    | `PATCH`   | `/schedule/{id}` |
| 일정 삭제    | `DELETE`  | `/schedule/{id}` |

<br>

## 1. 일정 생성 API

| **Method** | **엔드포인트**          |
|----------|----------------------|
| `POST`    | `/api/schedule`      |

#### 요청 본문
```json
{
  "title": "새로운 일정 제목",
  "content": "일정 내용"
}
```
#### 응답 예시
- 성공
```json
{
  "message": "일정이 등록되었습니다.",
  "success": true
}
```
- 로그인 상태가 아닐 때
```json
{
  "message": "로그인이 필요합니다.",
  "success": false
}
```
#### 상태 코드
| **상태**              | **응답 코드**   | **메시지**            |
|-----------------------|-----------------|--------------------|
| **성공**              | `201 CREATED`   | 일정이 등록되었습니다.  |
| **로그인 상태가 아님** | `401 UNAUTHORIZED` | 로그인이 필요합니다.  |


<br>

## 2. 일정 전체 조회
| **Method** | **엔드포인트**          |
|---------|-----------------------|
| `GET`    | `/api/schedule`      |

#### 요청 쿼리
| **파라미터**    | **설명**                    |
|-------------|-------------------------------|
| `id`        | 게시물 번호 (선택 사항)           |
| `updatedAt` | yyyy-MM-DD 형식의 날짜 (선택 사항)| 
#### 예시 요청
`GET` /schedule?userId=&updatedAt=2024-12-10
#### 응답 예시
- 성공
```json
[
  {
    "id": 1,
    "userId": "유저 id",
    "nickname": "유저 닉네임",
    "title": "일정 제목 1",
    "content": "일정 내용 1",
    "updatedAt": "2024-12-10T10:00:00"
  },
  {
    "id": 2,
    "userId": "유저 id",
    "nickname": "유저 닉네임",
    "title": "일정 제목 2",
    "content": "일정 내용 2",
    "updatedAt": "2024-12-10T10:00:00"
  }
]
```

#### 상태 코드
| **상태**              | **응답 코드**         | 
|-----------------------|-------------------|
| **성공**              | `200 OK`          |

<br>

## 3. 일정 단건 조회
| **Method** | **엔드포인트**          |
|---------|-----------------------|
| `GET`    | `/schedule/{id}`      |

#### 요청 쿼리
| **파라미터** | **설명** |
|----------|------------|
| `id`     | 게시물 번호 |

#### 예시 요청
`GET` /schedule/1
#### 응답 예시
- 성공
```json
{
  "id": 1,
  "userId": "유저 id",
  "nickname": "유저 닉네임",
  "title": "일정 제목 1",
  "content": "일정 내용 1",
  "updatedAt": "2024-12-10T10:00:00"
}
```

#### 상태 코드
| **상태**              | **응답 코드**       | 
|-----------------------|------------------|
| **성공**              | `200 OK`         |

<br>

## 4. 일정 수정
| **Method** | **엔드포인트**          |
|------------|------------------------|
| `PATCH`    | `/schedule/{id}`      |

#### 요청 본문
```json
{
  "title": "수정된 일정 제목",
  "content": "수정된 일정 내용"
}
```
#### 응답 예시
- 성공
```json
{
  "message": "일정이 수정되었습니다.",
  "success": true
}
```
- 로그인 상태가 아닐 때
```json
{
  "message": "일정이 수정되었습니다.",
  "success": true
}
```
- 수정 권한이 없을 때
```json
{
  "message": "수정 권한이 없습니다.",
  "success": false
}
```
#### 상태 코드
| **상태**              | **응답 코드**  | **메시지**    |
|-----------------------|----------------|------------|
| **성공**              | `200 OK`   | 일정이 수정되었습니다. |
| **로그인 상태가 아님** | `401 UNAUTHORIZED` | 로그인이 필요합니다.|
| **수정 권한이 없음** | `403 FORBIDDEN` | 수정 권한이 없습니다. |

<br>

## 5. 일정 삭제
| **Method** | **엔드포인트**          |
|------------|------------------------|
| `DELETE`   | `/schedule/{id}`      |

#### 응답 예시
- 성공
```json
{
  "message": "일정이 삭제되었습니다.",
  "success": true
}
```
- 로그인 상태가 아닐 때
```json
{
  "message": "로그인이 필요합니다.",
  "success": false
}
```
- 수정 권한이 없을 때
```json
{
  "message": "삭제 권한이 없습니다.",
  "success": false
}
```
#### 상태 코드
| **상태**         | **응답 코드**  | **메시지**      |
|----------------|----------------|--------------|
| **성공**         | `200 OK`   | 일정이 삭제되었습니다. |
| **로그인 상태가 아님** | `401 UNAUTHORIZED` | 로그인이 필요합니다.  |
| **삭제 권한이 없음**  | `403 FORBIDDEN` | 삭제 권한이 없습니다. |

<br>

## 인증 관련 API
| 기능   | Method  | 엔드포인트               |
|------|---------|---------------------|
| 회원가입 | `POST`   | `/api/auth/users`    |
| 로그인  | `POST`   | `/api/auth/sessions` |
| 로그아웃 | `DELETE` | `/api/auth/sessions`  |

<br>

## 1. 회원가입

| **Method** | **엔드포인트** |
|--------|-------|
| `POST`  | `/api/auth/users` |

#### 요청 본문
```json
{
  "username": "user123",
  "password": "password123",
  "email": "user@example.com"
}
```
#### 응답 예시
- 성공
```json
{
  "message": "회원가입이 완료되었습니다.",
  "success": true
}
```

#### 상태 코드
| **상태**              | **응답 코드** | **메시지**                       |
|-----------------------|-----------|---------------------------------|
| **성공**              | `200 OK`  | 회원가입이 완료되었습니다.         | 


<br>

## 2. 로그인

| **Method** | **엔드포인트**        |
|----------|-----------------------|
| `POST`    | `api/auth/sessions`      |

#### 요청 본문
```json
{
  "userId": "사용자 id",
  "password": "사용자 비밀번호"
}
```
#### 응답 예시
- 성공
```json
{
  "message": "로그인 성공",
  "success": true
}
```
- 아이디 또는 비밀번호 오류
```json
{
  "message": "로그인 실패",
  "success": false
}
```
#### 상태 코드
| **상태** | **응답 코드**     | **메시지** |
|--------|---------------|---------|
| **성공** | `200 OK`      | 로그인 성공  | 
| **실패** | `400 CREATED` | 로그인 실패  |

<br>

## 3. 로그아웃

| **Method** | **엔드포인트**         |
|---------|------------------------|
| `DELETE` | `api/auth/sessions`       |

#### 응답 예시
- 성공
```json
{
  "message": "로그아웃 성공",
  "success": true
}
```
#### 상태 코드
| **상태** | **응답 코드**     | **메시지** |
|--------|---------------|---------|
| **성공** | `200 OK`      | 로그아웃 성공 | 

<br>

## 📊ERD 다이어그램

<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FPiXfd%2FbtsLctCZ1az%2FmIWB7SbkIiJ5kxvd0A38k0%2Fimg.png" alt="이미지 설명" width="500"/>