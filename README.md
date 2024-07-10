# spring-gift-jpa


# step2 과제
1. wishlist.java(model)-수정 <br>
wishlist에는 상품정보와 유저정보가 들어가야하기 때문에
product 와 user에 @ManyToOne 태그를 걸었습니다. <br>
2.  문제발생 <br>
UserRepository에서 findByUsername 으로 username을 찾아야하는데
wishlist 엔티티를 수정하면서 user 객체까지만 불러올수 있음
3. 해결책 - SiteUserRepository 생성 <br>
