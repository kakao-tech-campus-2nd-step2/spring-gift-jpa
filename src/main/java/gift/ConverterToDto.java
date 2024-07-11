package gift;

import gift.DTO.ProductDto;
import gift.DTO.ProductEntity;
import gift.DTO.UserDto;
import gift.DTO.UserEntity;

public class ConverterToDto {

  public static ProductDto convertToDto(ProductEntity productEntity) {
    ProductDto productDto = new ProductDto(productEntity.getId(), productEntity.getName(),
      productEntity.getPrice(), productEntity.getImageUrl());
    return productDto;
  }

  public static UserDto convertToUserDto(UserEntity userEntity){
    UserDto userDto = new UserDto(userEntity.getId(),userEntity.getEmail(),userEntity.getPassword());
    return userDto;
  }
}
