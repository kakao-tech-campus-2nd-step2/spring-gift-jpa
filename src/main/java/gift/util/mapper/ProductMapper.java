package gift.util.mapper;

import gift.dto.product.AddProductRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.UpdateProductRequest;
import gift.entity.Product;

public class ProductMapper {

    public static Product toProduct(AddProductRequest addProductRequest) {
        return new Product(addProductRequest.id(), addProductRequest.name(),
            addProductRequest.price(), addProductRequest.imageUrl());
    }

    public static Product toProduct(Long id, UpdateProductRequest updateProductRequest) {
        return new Product(id, updateProductRequest.name(), updateProductRequest.price(),
            updateProductRequest.imageUrl());
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.id());
    }

}
