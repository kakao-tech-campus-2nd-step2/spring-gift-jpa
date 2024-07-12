# spring-gift-jpa
## Step1
- 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
  - [x] Entity 클래스 작성
  - [X] JpaRepository 작성
  - [X] Service 리팩토링
- @DataJpaTest를 사용하여 학습 테스트를 해본다.
  - [X] ProductRepositoryTest
  - [X] UserRepositoryTest
  - [X] WishListRepositoryTest

## Step2
- 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
  - [x] Entity로 리팩토링
  - [x] JpaRepository로 변경 및 추가적으로 필요한 JPA 메서드 작성
  - [X] Repository 변경으로 인한 Service, Controller 코드 수정
  - [x] JPA Test 작성

## Step3
- 상품과 위시 리스트 보기에 페이지네이션을 구현한다.
[x] Product 페이지네이션 구현
[x] WishList 페이지네이션 구현
