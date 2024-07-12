package gift.dto.responseDTO;


import gift.domain.Product;

public record ProductResponseDTO(Long id, String name, int price, String imageUrl) {
    public static ProductResponseDTO of(Product product) {
        return new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl());
    }
}
