# [구현할 기능 목록] 1단계 - 엔티티 매핑
### 1. spring data jpa 의존성 추가
- [x] `implementation 'org.springframework.boot:spring-boot-starter-data-jpa'`

### 2. 지금까지 작성한 JdbcTemplate 기반 코드를 JPA 기반 코드로 리팩토링
- [x] Login/repository 리팩토링
- [x] Login/model 리팩토링 
### 3. 테스트해보기
- [x] E2E Test
  - [x] 회원가입 테스트
  - [x] 로그인 테스트
- [x] @DataJpaTest
    - [x] wishlist CRUD 테스트

- [x] 전체적인 리팩토링 
  - [x] 멤버 서비스 인터페이스 생성
  - [ ] Deprecated API 최신형 API로 변경
  - [ ] 2주차 PR 리뷰 반영
  - [ ] MemberId를 UUID로 구현
  - [ ] @OneToMany, @ManyToOne으로 연관관계 매핑 리팩토링 
