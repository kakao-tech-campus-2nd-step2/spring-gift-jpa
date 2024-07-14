# spring-gift-jpa
## 1단계
### 기능 요구 사항
지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.

- 아래의 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다.
- @DataJpaTest를 사용하여 학습 테스트를 해 본다.
### 구현할 기능 목록
- Jdbc Template 기반 코드를 JPA로 리팩터링
  - [x] Product
  - [x] Member
  - [x] WishList
- [x] @DataJpaTest 사용해서 테스트 코드 작성
## 2단계
### 기능 요구 사항
객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.
### 구현할 기능 목록
- [x] Product 도메인 객체와 WishList 도메인 객체 간의 연관관계 매핑 
- [x] Member 도메인 객체와 WishList 도메인 객체 간의 연관관계 매핑
## 3단계
### 기능 요구 사항
상품과 위시 리스트 보기에 페이지네이션을 구현한다.
### 구현할 기능 목록
- [x] WishListService 와 WishListController 에서 페이지네이션 구현
- [x] ProductService 와 ProductController 에서 페이지네이션 구현