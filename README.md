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
  - [X] addWish() 테스트
  - [X] removeWish() 테스트

- ProductRepository
  - [X] Cascade Persist 테스트 (Product 저장 시, 연관된 Wish 객체들도 자동으로 저장되는지 확인)
  - [X] Cascade Remove 테스트 (Product 삭제 시, 연관된 Wish 객체들도 자동으로 삭제되는지 확인)
  - [X] OrphanRemoval 테스트 (Product의 wish 리스트에서 wish 삭제 시, 해당 Wish 객체가 데이터 베이스에서 사라지는지 확인)
  - [X] Lazy Fetch 테스트 (연관된 엔티티 필드에 접근할 때 데이터베이스 쿼리가 실행되는지 확인)

- Member
  - [X] addWish() 테스트
  - [X] removeWish() 테스트

- MemberRepository
  - [X] Cascade Persist 테스트
  - [X] Cascade Remove 테스트
  - [X] OrphanRemoval 테스트
  - [X] Lazy Fetch 테스트

## step3
### 기능 요구 사항
상품과 위시 리스트 보기에 페이지네이션을 구현한다.
- 대부분의 게시판은 모든 게시글을 한 번에 표시하지 않고 여러 페이지로 나누어 표시한다. 정렬 방법을 설정하여 보고 싶은 정보의 우선 순위를 정할 수도 있다.
- 페이지네이션은 원하는 정렬 방법, 페이지 크기 및 페이지에 따라 정보를 전달하는 방법이다.

### 구현할 기능 목록
페이지네이션 적용
- [X] Wish
- [X] Product

테스트 코드 작성
- [X] 상품 페이지 조회 테스트 (ProductRepository)
- [X] 위시 페이지 조회 테스트 (WishRepository)