# spring-gift-jpa

<details>
<summary>1단계 요구사항 자세히 보기</summary>

## 🚀 1단계 - 엔티티 매핑

### 개요
- JdbcTemplate 기반 코드를 JPA로 리팩터링
- @DataJpaTest를 이용한 학습 테스트

### 기능 목록
- [X] Member 엔티티 매핑
- [X] Product 엔티티 매핑
- [X] Wish 엔티티 매핑

</details>
<br>

## 🚀 2단계 - 연관 관계 매핑

### 개요
- JdbcTemplate 기반 코드를 JPA로 리팩터링
- 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용

### 기능 목록
- [X] Member와 Wish의 연관 관계 매핑
- [X] Product와 Wish의 연관 관계 매핑

### 기술 스택
- Java 21
- Spring Boot 3.3.1
- Gradle 8.4
