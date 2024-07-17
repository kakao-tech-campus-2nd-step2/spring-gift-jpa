# spring-gift-jpa


# JPA - ORM : step 1

JdbcTemplate 기반 코드를 JPA 적용 시키기 -> 도메인 모델 구성, 객체와 테이블 매핑

ORM 기반 - 관계 지향형 모델

spring data jpa 를 사용하도록 한다. 

-[X] : feat : ORM 모델 추가 (product, member, wishlist)

이때 wishlist는 many to many 모델이 되어야함.

-[X] : Repository 수정 -> spring data jpa를 적용시키도록 한다.

-[X] : test 코드 추가 -> 생각했던 목적에 맞게 동작하는 지 확인한다.

# step 2 연관관계 맵핑

- [X] : WishList와 member를 수정하여 정상 동작할 수 있도록 만들기
- [X] : wishlist 테스트 코드 작성


# step 3 페이지 구현하기

상품 목록 조회를 먼저 구현해보자.

1. Page 관련 Repository에 interface 추가해주기
2. 서비스에 해당 리포지토리를 통해 Pageable 객체를 통해 Page<T> 로 처리해주기
3. 컨트롤러 구현

wish 구현

1. wish interface 구현하기
2. 서비스 구현
3. 컨트롤러 구현

추가

관리자 페이지로 만들어보기
