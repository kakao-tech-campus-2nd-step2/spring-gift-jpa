package gift.util.mapper;

import gift.dto.product.AddProductRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.UpdateProductRequest;
import gift.entity.Product;

public class ProductMapper {

    public static Product toProduct(AddProductRequest addProductRequest) {
        return Product.builder()
            .name(addProductRequest.name())
            .price(addProductRequest.price())
            .imageUrl(addProductRequest.imageUrl())
            .build();
    }

    public static void updateProduct(Product product, UpdateProductRequest request) {
        product.changeName(request.name());
        product.changePrice(request.price());
        product.changeImageUrl(request.imageUrl());
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl());
    }

}
