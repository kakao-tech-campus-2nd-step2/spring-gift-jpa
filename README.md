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

### 4. Login 디렉토리 전부 리팩토링
- [ ] controller 리팩토링 (예외처리는 전부 서비스에서!!!)
- [ ] service 리팩토링
- [ ] errordto 만들기 
