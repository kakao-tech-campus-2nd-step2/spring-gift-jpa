# spring-gift-jpa


# step2 과제
1. wishlist.java(model)-수정 <br>
wishlist에는 상품정보와 유저정보가 들어가야하기 때문에
product 와 user에 @ManyToOne 태그를 걸었습니다. <br>
2.  Wilshlist 엔티티에 영향받는 코드들 수정 <br>
- WishlistServiceImpl 
- WishlistControllerTest
- WIshlistRepositoryTest
