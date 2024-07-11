# spring-gift-jpa

## step1
### 기능 요구 사항
지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
- DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다.
- @DataJpaTest를 사용하여 학습 테스트를 해 본다.

### 구현할 기능 목록
엔티티 맵핑
- [X] product
- [X] member
- [X] wish

JPA repository로 수정
- [X] ProductRepository
- [X] MemberRepository
- [X] WishRepository

JPA 테스트
- [X] ProductRepository
- [X] MemberRepository
- [X] WishRepository

## step2
### 기능 요구 사항
지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
- 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.

### 구현할 기능 목록
양방향 OneToMany 설계
- [X] Member
- [X] Product
- [X] Wish

양방향 OneToMany 설정 후 테스트
- Product
  - [ ] addWish() 테스트
  - [ ] removeWish() 테스트

- ProductRepository
  - [ ] Cascade Persist 테스트 (Product 저장 시, 연관된 Wish 객체들도 자동으로 저장되는지 확인)
  - [ ] Cascade Remove 테스트 (Product 삭제 시, 연관된 Wish 객체들도 자동으로 삭제되는지 확인)
  - [ ] OrphanRemoval 테스트 (Product의 wish 리스트에서 wish 삭제 시, 해당 Wish 객체가 데이터 베이스에서 사라지는지 확인)
  - [ ] Lazy Fetch 테스트 (연관된 엔티티 필드에 접근할 때 데이터베이스 쿼리가 실행되는지 확인)

- Member
  - [ ] addWish() 테스트
  - [ ] removeWish() 테스트

- MemberRepository
  - [ ] Cascade Persist 테스트
  - [ ] Cascade Remove 테스트
  - [ ] OrphanRemoval 테스트
  - [ ] Lazy Fetch 테스트
