# spring-gift-jpa

## step1 - JPA 적용

1. 기존 코드 리팩토링
    - [x] 엔티티, 레포지토리 작성
    - [x] 그에 맞게 서비스 수정
    - [x] Dao 삭제

2. 테스트 코드 작성
    - [x] 기존 Service 테스트 수정
    - [x] `@DataJpaTest` 이용하여 테스트 작성
    - [ ] 새로운 E2E 테스트 작성

## step2 - 엔티티 모델링

1. 엔티티 연관 관계 설정
   - [x] `Wish` 엔티티가 `User`, `Product` 참조하도록 함
   - [x] `user_id`, `product_id`를 FK로 가지도록 설정

## step3 - 페이지네이션
   - [ ] `Product` 에 대해 페이지네이션 구현
   - [ ] `Wish` 에 대해 페이지네이션 구현
   
