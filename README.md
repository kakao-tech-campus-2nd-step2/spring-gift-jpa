# spring-gift-wishlist
## step1
### 기능 요구 사항
    지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩토링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는 지 알아본다
    @DataJpaTest 사용하여 학습 테스트를 해 본다
    아래 DDL을 참고하여 엔티티 클래스와 리포지토리 클래스를 작성해본다.
### 구현할 기능
    앤티티 매핑
        - [x] product 
        - [x] member
        - [x] wish 
    JPA repository로 수정
        - [x] productRepository
        - [x] memberRepository
        - [x] wishRepository 
    JPA 테스트
        - [x] productRepositoryTest 
        - [x] memberRepositoryTest 
        - [x] wishRepositoryTest 

## step2
### 기능 요구 사항
    지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
    객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.
### 구현할 기능
    외래키 매핑
        - [x] Wish 도메인의 외래키 user_id, product_id를 객체 참조하는 방법으로 구현 

## step3
### 기능 요구 사항
    상품과 위시 리스트 보기에 페이지네이션을 구현한다.
### 구현할 기능
    - [x] Controller
        페이지네이션으로 wish 조회 api 생성
    - [x] Service
    - [x] Repository
        wish를 페이지네이션하여 반환
    - [x] Domain
        페이지 정렬을 위한 생성일 정보 속성 추가