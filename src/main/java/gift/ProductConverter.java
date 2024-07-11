package gift;

import gift.DTO.ProductDto;
import gift.DTO.ProductEntity;

public class ProductConverter {

  public static ProductDto convertToDto(ProductEntity productEntity) {
    ProductDto productDto = new ProductDto(productEntity.getId(), productEntity.getName(),
      productEntity.getPrice(), productEntity.getImageUrl());
    return productDto;
  }
}
