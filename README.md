# spring-gift-jpa

## step1

### 기능 요구 사항

지금까지 작성한 JdbcTemplate 기반 코드(https://github.com/jjt4515/spring-gift-wishlist/tree/step3) 를 
JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.

엔티티 클래스와 리포지토리 클래스를 작성해 본다.
@DataJpaTest를 사용하여 학습 테스트를 해 본다.

### 구현 기능 목록

- JPA 설정
  - application.properties 파일 설정
    - H2 데이터베이스 설정
    - Hibernate SQL 로그 설정

- 엔티티 클래스 작성
  - Member 엔티티 클래스 작성
  - Product 엔티티 클래스 작성
  - Wish 엔티티 클래스 작성
  - Token 엔티티 클래스 작성
      
- 리포지토리 클래스 작성
  - MemberJpaRepository 작성
  - ProductJpaRepository 작성
  - WishJpaRepository 작성
  - TokenJpaRepository 작성

- 테스트 코드 작성
  - MemberRepository 테스트 작성
  - MemberService 테스트 작성
  - ProductRepository 테스트 작성
  - ProductService 테스트 작성
  - WishRepository 테스트 작성
  - WishService 테스트 작성
  - TokenRepository 테스트 작성
  - TokenService 테스트 작성

## step 2

### 기능 요구 사항

지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.

객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.

### 구현 기능 목록

- 엔티티 간의 연관 관계 매핑

    - Member와 WishlistItem 간의 일대다 관계 매핑
    - Product와 WishlistItem 간의 일대다 관계 매핑
    - TokenAuth와 Member 간의 일대일 관계 매핑

## step3

### 기능 요구 사항

상품과 위시 리스트 보기에 페이지네이션을 구현한다.

대부분의 게시판은 모든 게시글을 한 번에 표시하지 않고 여러 페이지로 나누어 표시한다. 정렬 방법을 설정하여 보고 싶은 정보의 우선 순위를 정할 수도 있다.
페이지네이션은 원하는 정렬 방법, 페이지 크기 및 페이지에 따라 정보를 전달하는 방법이다.

### 구현 기능 목록

- 상품 목록 페이지네이션
    - 상품 리스트를 페이지별로 조회할 수 있어야 함.
    - 한 페이지에 표시될 상품의 수는 설정 가능해야 함.
    - 상품은 특정 기준으로 정렬할 수 있어야 함 (예: 이름, 가격).

- 위시 리스트 페이지네이션
    - 회원의 위시 리스트를 페이지별로 조회할 수 있어야 함.
    - 한 페이지에 표시될 위시 아이템의 수는 설정 가능해야 함.
    - 위시 리스트는 특정 기준으로 정렬할 수 있어야 함 (예: 추가된 날짜).
