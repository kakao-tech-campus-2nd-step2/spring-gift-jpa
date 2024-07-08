package gift;

import gift.model.Product;
import gift.model.ProductDTO;

public class ProductConverter {

    public static ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl());
    }

    public static Product convertToEntity(ProductDTO productDTO) {
        return new Product(
            productDTO.getId(),
            productDTO.getName(),
            productDTO.getPrice(),
            productDTO.getImageUrl());
    }
}