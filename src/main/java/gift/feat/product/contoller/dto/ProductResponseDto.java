package gift.feat.product.contoller.dto;

import gift.feat.product.domain.Product;

public record ProductResponseDto(
	String name,
	Long price,
	String imageUrl
) {
	static public ProductResponseDto from(Product product) {
		return new ProductResponseDto(
			product.getName(),
			product.getPrice(),
			product.getImageUrl()
		);
	}
}
