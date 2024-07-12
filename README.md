# spring-gift-jpa


# step3 과제
1.  repository <br>
pageable - 페이지 네이션 정보를 담고 있음<br>
Page<Product> findAll() - pageable 을 모두 찾아서 페이지 형식으로 반환
2.  service  <br>
findBy- 어떤 기준으로 find 해서 page 형식으로 반환
3. controller
기본페이지는 0 , 한 페이지당 상품수는 3개로 정하고
페이지 정보를 html로 넘김


# 수정한 코드
- ProductWebController 
- ProductRepository
- ProductService
- ProductServiceImpl
- productList.html
- WishlistController
- WishlistRepository
- WishlistService
- WishlistServiceImpl
- wishlist,html


# #  추가수정
정렬기능을 구현하지 않았는데 , 다시 과제 요구 사항을 확인하고 
정렬기능을 추가함 <br>
상품 리스트는 ID , price , name을 기준으로 정렬할수 있는 버튼 구현<br>
위시리스트는 price , name , quantity를 기준으로 정렬할수 있는 버튼 구현<br>

