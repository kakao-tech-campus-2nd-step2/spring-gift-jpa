package gift;

import gift.DTO.Member;
import gift.DTO.MemberDto;
import gift.DTO.Product;
import gift.DTO.ProductDto;
import gift.DTO.WishList;
import gift.DTO.WishListDto;

public class ConverterToDto {

  public static ProductDto convertToProductDto(Product product) {
    ProductDto productDto = new ProductDto(product.getId(), product.getName(),
      product.getPrice(), product.getImageUrl());
    return productDto;
  }

  public static MemberDto convertToUserDto(Member member) {
    MemberDto memberDto = new MemberDto(member.getId(), member.getEmail(), member.getPassword());
    return memberDto;
  }

  public static WishListDto convertToWishListDto(WishList wishList) {
    ProductDto productDto = convertToProductDto(wishList.getProduct());
    MemberDto memberDto = convertToUserDto(wishList.getMember());
    WishListDto wishListDto = new WishListDto(wishList.getId(), memberDto, productDto);
    return wishListDto;
  }
}
