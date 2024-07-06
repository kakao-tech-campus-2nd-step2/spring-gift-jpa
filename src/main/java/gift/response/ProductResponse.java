package gift.response;

import gift.model.Product;

public record ProductResponse(Long id, String name, int price, String imageUrl) {

    public ProductResponse(Product product) {
        this(product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl());
    }
}
