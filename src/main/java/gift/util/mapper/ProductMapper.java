package gift.util.mapper;

import gift.dto.product.AddProductRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.UpdateProductRequest;
import gift.entity.Product;

public class ProductMapper {

    public static Product toProduct(AddProductRequest addProductRequest) {
        return Product.builder()
            .id(addProductRequest.id())
            .name(addProductRequest.name())
            .price(addProductRequest.price())
            .imageUrl(addProductRequest.imageUrl())
            .build();
    }

    public static Product toProduct(Long id, UpdateProductRequest updateProductRequest) {
        return Product.builder()
            .id(id)
            .name(updateProductRequest.name())
            .price(updateProductRequest.price())
            .imageUrl(updateProductRequest.imageUrl())
            .build();
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId());
    }

}
