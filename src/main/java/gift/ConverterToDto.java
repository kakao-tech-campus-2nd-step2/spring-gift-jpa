package gift;

import gift.DTO.ProductDto;
import gift.DTO.ProductEntity;
import gift.DTO.UserDto;
import gift.DTO.UserEntity;
import gift.DTO.WishListDto;
import gift.DTO.WishListEntity;

public class ConverterToDto {

  public static ProductDto convertToProductDto(ProductEntity productEntity) {
    ProductDto productDto = new ProductDto(productEntity.getId(), productEntity.getName(),
      productEntity.getPrice(), productEntity.getImageUrl());
    return productDto;
  }

  public static UserDto convertToUserDto(UserEntity userEntity){
    UserDto userDto = new UserDto(userEntity.getId(),userEntity.getEmail(),userEntity.getPassword());
    return userDto;
  }

  public static WishListDto convertToWishListDto(WishListEntity wishListEntity){
    WishListDto wishListDto = new WishListDto(wishListEntity.getId(),wishListEntity.getUserId(),wishListEntity.getProductId());
    return wishListDto;
  }
}
