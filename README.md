# spring-gift-jpa

# 1단계 과제

## 기능 요구 사항
- 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.

- 아래의 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다.
- @DataJpaTest를 사용하여 학습 테스트를 해 본다.
  ```
    create table member
    (
    id       bigint generated by default as identity,
    email    varchar(255) not null unique,
    password varchar(255) not null,
    primary key (id)
    )
  ```
  ```
    create table product
    (
    price     integer      not null,
    id        bigint generated by default as identity,
    name      varchar(15)  not null,
    image_url varchar(255) not null,
    primary key (id)
    )
  ```
  ```
    create table wish
    (
    id         bigint generated by default as identity,
    member_id  bigint not null,
    product_id bigint not null,
    primary key (id)
    )
  ```
## 구현할 기능 목록
- [x] JDBCTemplate 기반 코드 JPA로 리팩토링

  - [x] JPA 사용을 위한 dependencies 추가
  
  - [x] 엔티티 클래스 작성
    - [x] member table
      - [x] id(Long, key), email(Varchar, unique), password(Varchar)
    - [x] product
      - [x] id(Long, key), price(Integer, 0이상), name(Varchar, not null), image_url(Varchar, not null) 
    - [x] wish
      - [x] id(Long, key), member_id(Long, not null), product_id(Long, not null)
      
  - [x] 리포지토리 인터페이스 작성
    - [x] MemberRepository 인터페이스 작성
    - [x] ProductRepository 인터페이스 작성
    - [x] WishRepository 인터페이스 작성
  
  - [x] 리포지토리 구현체 작성
    - [x] MemberRepositoryImpl 클래스 작성
    - [x] ProductRepositoryImpl 클래스 작성
    - [x] WishRepositoryImpl 클래스 작성
    
- [x] 동작 쿼리를 로그로 확인하도록 property 설정

- [x] MySQL Dialect을 사용을 위한 property 추가

- [ ] 학습 테스트 작성
  - [ ] @DataJpaTest 어노테이션을 사용하여 테스트 클래스 작성
    - [x] MemberRepositoryTest 작성
    - [x] ProductRepositoryTest 작성 
    - [ ] WishRepositoryTest 작성

## 1주차 과제 피드백
- [x] spring Security 관련 내용 삭제 및 비활성화
  - [x] build 파일에서 관련 내용 비활성화
  - [x] 사용하지 않는 로직 삭제
- [x] MemberController header key 변경
- [ ] 공통 Exception을 ControllerAdvice로 고치기
- [ ] MemberController 