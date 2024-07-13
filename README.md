# spring-gift-jpa
> 카카오테크 캠퍼스 STEP2 - 3주차 클론 코딩

## 목차
[* 코드 소개](#코드-소개)<br>
[* 0 단계 - 기본 코드 준비](#-0-단계----기본-코드-준비)<br>
[* 1 단계 - 엔티티 매핑](#-1-단계----엔티티-매핑)<br>
[* 2 단계 - 연관 관계 매핑](#-2-단계----연관-관계-매핑)<br>
[* 3 단계 - 페이지네이션](#-3-단계----페이지네이션)<br>

## 코드 소개
카카오 선물하기의 상품을 관리하는 서비스를 구현해본다

## < 0 단계 > - 기본 코드 준비
### [ 기능 요구 사항 ]
- [spring-gift-wishlist](https://github.com/chris0825/spring-gift-wishlist/tree/main)의 코드를 옮겨온다.

## < 1 단계 > - 엔티티 매핑
### [ 기능 요구 사항]
> 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
- 엔티티 클래스와 리포지토리 클래스를 작성
- @DataJpaTest를 사용하여 학습 테스트를 진행

## < 2 단계 > - 연관 관계 매핑
### [ 기능 요구 사항 ]
> 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
- 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.
```h2
alter table if exists wish
    add constraint fk_wish_member_id_ref_member_id
    foreign key (member_id)
    references member

alter table if exists wish
    add constraint fk_wish_product_id_ref_product_id
    foreign key (product_id)
    references product
```

## < 3 단계 > - 페이지네이션
### [ 기능 요구 사항 ]
> 상품과 위시 리스트 보기에 페이지네이션 구현
- 상품 목록 조회시 페이지네이션 적용
- 위시 리스트 조회시 페이지네이션 적용
