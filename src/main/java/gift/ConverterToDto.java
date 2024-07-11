package gift;

import gift.DTO.ProductDto;
import gift.DTO.Product;
import gift.DTO.UserDto;
import gift.DTO.User;
import gift.DTO.WishListDto;
import gift.DTO.WishList;

public class ConverterToDto {

  public static ProductDto convertToProductDto(Product product) {
    ProductDto productDto = new ProductDto(product.getId(), product.getName(),
      product.getPrice(), product.getImageUrl());
    return productDto;
  }

  public static UserDto convertToUserDto(User user){
    UserDto userDto = new UserDto(user.getId(), user.getEmail(), user.getPassword());
    return userDto;
  }

  public static WishListDto convertToWishListDto(WishList wishList){
    WishListDto wishListDto = new WishListDto(wishList.getId(), wishList.getUserId(), wishList.getProductId());
    return wishListDto;
  }
}
