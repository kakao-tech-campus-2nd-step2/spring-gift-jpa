# spring-gift-jpa

# step 1

~

# step 2

지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.

### product(OneToMany) &lrarr; product_wishlist(ManyToOne) &lrarr; wishlist(OneToMany)

1. productId 지정해서 wishlist 삭제 가능
2. 여러 user의 wishlist 동시에 관리 가능
3. 특정 상품을 담은 유저들을 모두 볼 수 있음