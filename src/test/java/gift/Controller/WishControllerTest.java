package gift.Controller;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.DTO.MemberDto;
import gift.DTO.ProductDto;
import gift.DTO.WishListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class WishControllerTest {

  WishController wishController;
  ProductController productController;
  MemberController memberController;


  @Autowired
  public WishControllerTest(WishController wishController, ProductController productController,
    MemberController memberController) {
    this.wishController = wishController;
    this.productController=productController;
    this.memberController=memberController;
  }

  @Test
  void getWishList() {
    Pageable pageable = PageRequest.of(0, 5);
    MemberDto memberDto1 = new MemberDto(1L, "a@naver.com", "abcde");
    memberController.userSignUp(memberDto1);

    ProductDto productDto1 = new ProductDto(1L, "product1", 100, "abcd.img");
    ProductDto productDto2 = new ProductDto(2L, "product2", 200, "efgh.img");
    productController.addProduct(productDto1);
    productController.addProduct(productDto2);

    WishListDto wishListDto1 = new WishListDto(1L, memberDto1, productDto1);
    WishListDto wishListDto2 = new WishListDto(2L, memberDto1, productDto2);
    wishController.addProductToWishList(wishListDto1, null);
    wishController.addProductToWishList(wishListDto2, null);

    ResponseEntity<Page<WishListDto>> wishListDtoEntity = wishController.getWishList(pageable);
    Page<WishListDto> wishListDtos = wishListDtoEntity.getBody();

    assertThat(memberDto1.getId()).isEqualTo(
      wishListDtos.getContent().get(0).getMemberDto().getId());
    assertThat(memberDto1.getEmail()).isEqualTo(
      wishListDtos.getContent().get(0).getMemberDto().getEmail());
    assertThat(memberDto1.getPassword()).isEqualTo(
      wishListDtos.getContent().get(0).getMemberDto().getPassword());

    assertThat(productDto1.getId()).isEqualTo(
      wishListDtos.getContent().get(0).getProductDto().getId());
    assertThat(productDto1.getName()).isEqualTo(
      wishListDtos.getContent().get(0).getProductDto().getName());
    assertThat(productDto1.getPrice()).isEqualTo(
      wishListDtos.getContent().get(0).getProductDto().getPrice());
    assertThat(productDto1.getImageUrl()).isEqualTo(
      wishListDtos.getContent().get(0).getProductDto().getImageUrl());

    assertThat(productDto2.getId()).isEqualTo(
      wishListDtos.getContent().get(1).getProductDto().getId());
    assertThat(productDto2.getName()).isEqualTo(
      wishListDtos.getContent().get(1).getProductDto().getName());
    assertThat(productDto2.getPrice()).isEqualTo(
      wishListDtos.getContent().get(1).getProductDto().getPrice());
    assertThat(productDto2.getImageUrl()).isEqualTo(
      wishListDtos.getContent().get(1).getProductDto().getImageUrl());

  }

//  @Test
//  void addProductToWishList() {
//  }
//
//  @Test
//  void deleteProductToWishList() {
//  }
}