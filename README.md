# spring-gift-jpa

### 1단계 기능 요구 사항
- 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
  - [x] ProductRepository 생성
  - [x] MemberRepository 생성
  - [x] WishRepository 생성
  - [x] ProductDao에서 ProductRepository로 변경
    - [x] validate를 Product로 옮김.
  - [ ] MemberDao에서 MemberRepository로 변경
  - [ ] WishDao에서 WishRepository로 변경
@DataJpaTest를 사용하여 학습 테스트를 해 본다.
