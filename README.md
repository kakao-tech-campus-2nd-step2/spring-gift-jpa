# spring-gift-jpa

## 1단계 - 엔티티 매핑
### 미션 요구 사항
지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
* 아래의 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다.
* @DataJpaTest를 사용하여 학습 테스트를 해 본다.
    ```
    create table member
    (
        id       bigint generated by default as identity,
        email    varchar(255) not null unique,
        password varchar(255) not null,
        primary key (id)
    )
    ```
    ```
    create table product
    (
        price     integer      not null,
        id        bigint generated by default as identity,
        name      varchar(15)  not null,
        image_url varchar(255) not null,
        primary key (id)
    )

    ```
    ```
    create table wish
    (
        id         bigint generated by default as identity,
        member_id  bigint not null,
        product_id bigint not null,
        primary key (id)
    )
    ```

### 구현할 기능 목록
* 엔티티 클래스 추가
* 리포지토리 클래스 수정
* `@DataJpaTest` 테스트
