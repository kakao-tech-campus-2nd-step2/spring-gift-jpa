package gift.feat.product.contoller.dto;

import gift.core.annotaions.NoKakao;
import gift.core.annotaions.ValidCharset;
import gift.core.exception.ValidationMessage;
import gift.feat.product.domain.Product;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductRequestDto(
	@Size(max = 15, message = ValidationMessage.INVALID_SIZE_MSG)
	@ValidCharset
	@NoKakao
	String name,
	Long price,
	String imageUrl
) {
	public Product toEntity() {
		return Product.of(name, price, imageUrl);
	}
}
