package gift.dto;

import gift.constants.ErrorMessage;
import gift.constants.RegularExpression;
import gift.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductDto {

    private final Long id;

    @NotBlank(message = ErrorMessage.PRODUCT_NAME_VALID_NOT_BLANK_MSG)
    @Size(min = 1, max = 15, message = ErrorMessage.PRODUCT_NAME_VALID_SIZE_MSG)
    @Pattern(
        regexp = RegularExpression.PRODUCT_NAME_CHAR_VALID_REGEX,
        message = ErrorMessage.PRODUCT_NAME_VALID_CHAR_MSG)
    @Pattern(
        regexp = RegularExpression.PRODUCT_NAME_FIND_KAKAO_REGEX,
        message = ErrorMessage.PRODUCT_NAME_VALID_KAKAO_MSG)
    private final String name;

    @NotNull
    private final long price;

    private final String imageUrl;

    public ProductDto(Long id, String name, long price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductDto(Product p) {
        this(p.getId(), p.getName(), p.getPrice(), p.getImageUrl());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
