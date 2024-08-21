# 📝 게시판 프로젝트

Spring Boot 기반 웹 게시판 API

## 🔥 기능 설명

### 회원가입

* [X]  이메일 - 이메일 형식에 맞는지 검증
* [X]  휴대폰 번호 - 숫자와 하이폰으로 구성된 형식 검즘
* [X]  작성자 - 아이디 대소문자 및 한글 이름 검즘
* [X]  비밀번호 - 대소문자, 숫자 5개 이상, 특수문자 포함 2개 이상 검증

### 로그인

* [X]  스프링 시큐리티를 활용한 로그인 기능 구현

### 사용자 인증

* [X]  JWT를 이용한 사용자 인증

### 토큰 재발급

* [X] 쿠키에 저장된 Refresh 토큰을 기반으로 새로운 Access 토큰, Refresh 토큰 생성

### 로그아웃

* [X]  FilterChain에 CustomLogoutFilter 추가

### 게시글 등록

* [X]  제목 - 200글자 이하 제한
* [X]  내용 - 1000글자 이하 제한
* [X]  생성및 수정 시간 자동관리
* [X]  게시글에 이미지 첨부 기능 추가 (이미지 업로드 시 외부 스토리지 연동)

### 게시글 수정

* [X]  게시글 작성자만 수정 가능
* [X]  생성일 기준 10일 이후 수정불가

### 게시글 목록조회

* [X]  생성일 기준 내림차순 오름차순 정렬
* [X]  title 기준 부분 검색 가능
* [X]  title 이 없을 경우 cratedAt 정렬 기준으로 표시
* [X]  deletedAt 기준 삭제된 게시글 제외

### 게시글 상세보기

* [X]  수정 가능일 현재 날짜 기준 계산 및 표시
* [X]  게시글 조회수 증가 기능 (동일 사용자가 여러 번 조회 시 조회수 증가 방지)

### 게시글 좋아요 등록

* [X] 게시글 좋아요 및 취소 설정

### 게시글 삭제

* [X]  게시글 작성자만 삭제 가능
* [X]  Soft Delete 적용 deletedAt 사용하여 삭제처리

### 댓글 기능

게시글에 댓글 추가 기능

* [X]  댓글 작성, 수정, 삭제 (Soft Delete)

### 알림 기능

* [X]  수정 제한 경고 알림 (게시글 생성일 9일째 경고 알림 발신, SMTP 사용)

## 📌 ERD

<img width="1119" alt="스크린샷 2024-08-21 오후 5 35 06" src="https://github.com/user-attachments/assets/6d8b345f-982c-423a-85a9-c112ed8b074b">

## 📝 API Doc : Swagger

![screencapture-localhost-8080-swagger-ui-index-html-2024-08-21-17_15_03](https://github.com/user-attachments/assets/609e6e71-e376-4928-8edc-fc406f493189)

## 🧐 API Test : Postman

<img width="1330" alt="스크린샷 2024-08-21 오후 5 37 55" src="https://github.com/user-attachments/assets/df8ab608-8790-4ca3-9343-457efff32f81">
