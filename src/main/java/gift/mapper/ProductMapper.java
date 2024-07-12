package gift.mapper;

import gift.DTO.ProductDTO;
import gift.model.product.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toProductDTO(ProductEntity productEntity) {
        return new ProductDTO(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getPrice(),
            productEntity.getImageUrl()
        );
    }

    public ProductEntity toProductEntity(ProductDTO productDTO, boolean idRequired) {
        var productEntity = new ProductEntity();
        if (idRequired) {
            productEntity.setId(productDTO.id());
        }
        productEntity.setName(productDTO.name());
        productEntity.setPrice(productDTO.price());
        productEntity.setImageUrl(productDTO.imageUrl());
        return productEntity;
    }

    public ProductEntity toProductEntity(ProductDTO productDTO) {
        return toProductEntity(productDTO, true);
    }
}
