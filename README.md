# spring-gift-jpa

# step 1

~

# step 2

지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.

### wishlist &rarr; product (oneToMany)

```java
Wishlist wishlist = findWishlistByEmail(email);
List<Product> products = wishlist.getProducts();
```

위와 같은 방식으로 참조 가능하게...

### product &rarr; wishlist (ManyToOne)

### product(OneToMany) &lrarr; product_wishlist(ManyToOne) &lrarr; wishlist(OneToMany)