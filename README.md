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