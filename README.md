# spring-gift-jpa

## Step1 - ToDo List
1단계 - 엔티티 매핑

- 기능 요구 사항
  - JdbcTemplate 기반 코드를 JPA로 리팩터링
  - 도메인 모델 설계
  - Repository Test

- 구현 기능 목록
  - Builder Pattern 도입
  - Domain Entity 적용
  - Repository JdbcTemplate -> JPA 변경

## Step2 - ToDo List
- 2단계 - 연관 관계 매핑

- 기능 요구 사항
  - 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.

- 구현 기능 목록
  - 단방향 연관관계 설정
    - step1 에서 진행 했습니다.
  - 양방향 연관관계 설정
  - fetch join 쿼리 작성
  - fetch join test

## Step3 - ToDo List
- 3단계 - 페이지네이션

- 기능 요구 사항
  - 상품과 위시 리스트 보기에 페이지네이션을 구현한다.

- 구현 기능 목록
  - Entity create, update 시간 관리 기능
  - 상품 paging 기능
  - 위시 paging 기능