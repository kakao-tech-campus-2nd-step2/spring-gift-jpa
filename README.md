# spring-gift-jpa

## 엔티피 연관관계 매핑 요구사항 : 작성해야할 가능에 대해
- 객체와 테이블 매핑 ->> @Entity @OneToMany 등을 이용
- @DataJpaTest를 사용하여 테스트 코드 작성 : 위시리스트 레포지토리 계속되는 테스트 실패 오류


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
        │       │   ├── model
        │       │   │    └── Product.java
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
               └── MemberRepositoryTest.java