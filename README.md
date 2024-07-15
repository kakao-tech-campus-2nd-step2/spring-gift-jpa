# spring-gift-jpa

## 3주차 (1-2단계) 요구사항
지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
- DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다.
- @DataJpaTest를 사용하여 학습 테스트를 해 본다.
- Spring Data JPA 사용 시 아래 옵션은 동작 쿼리를 로그로 확인할 수 있게 해준다.
- H2 데이터베이스를 사용한다면 아래의 프로퍼티를 추가하면 MySQL Dialect을 사용할 수 있다.

## 작성할 기능
- 객체와 테이블 매핑 ->> @Entity @JoinColumn @JoinTable 등의 어노테이션 사용
- 테스트 코드 작성 ->> 굉장한 테스트 실패 오류의 반복


## 현재 코드 구조
```plaintext
└── src
    └── main
        ├── java
        │   └── gift
        │       ├── Application.java
        │       │
        │       ├── admin
        │       │   └── AdminController.java
        │       │ 
        │       ├── exception
        │       │   ├── ForbiddenException.java
        │       │   ├── UnauthorizedException.java
        │       │   ├── KakaoProductException.java
        │       │   └── GlobalExceptionHandler.java
        │       │ 
        │       ├── member
        │       │   ├── controller
        │       │   │    └── MemberController.java
        │       │   ├── dto
        │       │   │    └── MemberDto.java
        │       │   ├── model
        │       │   │    └── Member.java
        │       │   ├── repostitory
        │       │   │    └── MemberRepository.java
        │       │   └── service
        │       │        ├── MemberService.java
        │       │        └── TokenService.java
        │       │
        │       ├── product
        │       │   ├── controller
        │       │   │    └── ProductController
        │       │   ├── dto
        │       │   │    └── ProductDto.java
        │       │   ├── model
        │       │   │    ├── Product.java
        │       │   │    └── ProductName.java
        │       │   ├── repository
        │       │   │    └── ProductRepository.java
        │       │   └── service
        │       │        └── ProductService.java
        │       │
        │       └── wishlist
        │           ├── WishList.java
        │           ├── WishListController.java
        │           ├── WishListRepository.java
        │           └── WishListService.java
        │       
        └── resources
            ├── member.html
            ├── data.sql
            ├── schema.sql
            ├── static
            └── templates
                ├── add.html
                ├── edit.html
                ├── list.html
                └── view.html
 
                
└── src
    └── main
        ├── java
            └── gift    
               ├── MemberRepositoryTest.java
               ├── ProductRepositoryTest.java
               └── WishListService.java