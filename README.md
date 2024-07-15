# spring-gift-jpa

---
## 0단계 - 기본 코드 준비
- 구현할 기능
  - 위시 리스트 코드 옮기기
---
## 1단계 - 엔티티 매핑
- record 엔티티를 일반 클래스로 변경
  - Product, User, Wishlist 변경
- JdbcTemplate 기반 코드를 JPA로 리펙터링하기
  - 엔티티에 어노테이션 달기
    - Product, User, Wishlist
  - Repository를 JPA로 구현
    - ProductRepository, UserRepository, WishlistRepository
- Repository에 대한 Test 코드 작성
  - ProductRepositoryTest, UserRepositoryTest, WishlistRepository
---
## 2단계 - 연관 관계 매핑
- @ManyToOne 사용
  - Wishlist
- @OneToMany 사용
  - Member
  - Product
  - CASCADE 사용
- WishlistRepository 수정
- WishlistService 수정
- Test 코드 수정
---
## 3단계 - 페이지네이션
- WishlistRepository 수정
  - 조회할 때 Page 사용하도록 수정
- Repository 수정으로 인한 Service 수정
  - ProductService 수정
  - WishlistService 수정
- Controller 수정
  - AdminController
  - ProductController
  - WishlistController
- HTML 수정