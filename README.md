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
---
## 3단계 요구사항
### WishlistRepository
- Page<> 반환
### WishlistService
- Page<> 처리를 위한 메소드로 변경
### WishlistViewController
- 메소드에 page, size 파라미터 이용하도록 변경
### wishlist.html
- pagenation추가해서 페이지 이동할수 있도록 수정

### ProductRepository
- Page<> 반환
### ProductService
- Page<> 처리를 위한 메소드로 변경
### add_products.html, wishlist.html, products.html
- pagenation 추가해서 페이지 이동할 수 있도록 수정
