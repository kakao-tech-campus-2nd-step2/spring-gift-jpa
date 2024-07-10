package gift.feat.wishList;

import gift.feat.product.domain.Product;
import gift.feat.product.dto.ProductResponseDto;

public record WishProductResponseDto(
	ProductResponseDto product
) {
	static public WishProductResponseDto from(WishProduct wishProduct) {
		return new WishProductResponseDto(ProductResponseDto.from(wishProduct.getProduct()));
	}
}
