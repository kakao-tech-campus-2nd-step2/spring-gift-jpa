# spring-gift-jpa
---
## 1단계
1. Product, User, WishlistItem에 JPA 적용
2. ProductRepository, UserRepository, WishlistRepository에 JpaRepository<> 적용
3. @DataJpaTest 활용하여 각각의 학습 테스트
---
## 2단계 요구사항
### 연관관계 매핑
### WishlistItem
- userId -n:1- User.id
- productId -n:1- Product.id
