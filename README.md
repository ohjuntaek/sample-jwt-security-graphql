# jwt-security-graphQL 샘플 프로젝트

## 기능

1. 회원
- (회원이름, 회원아이디, 역할)
- 역할 
    - 관리자(ADMIN) : 다 가능
    - 일반(USER)
        - 자신의 회원 정보 R, 글 RUD
        - 남의 글 R
    - 비회원 : 회원가입, 메인 보기
- 회원가입/로그인 하기
    - 1. 시큐리티 연동
    - 2. jwt 적용

2. 게시글
- (게시글 제목, 게시글 내용, 작성자ID)


## To-Do

- [x] 회원가입 - 시큐리티
- [ ] 로그인 - jwt


## 참고 링크

- 너무 안되네~~ 

https://github.com/g00glen00b/whoiswho/blob/master/whoiswho-backend/src/main/java/be/g000glen00b/whoiswho/user/UserService.java
https://dimitr.im/graphql-spring-security