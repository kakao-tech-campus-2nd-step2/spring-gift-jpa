package gift.dto;

import gift.constants.ErrorMessage;
import gift.constants.RegularExpression;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Product(
    @NotNull
    long id,

    @NotBlank(message = ErrorMessage.PRODUCT_NAME_VALID_NOT_BLANK_MSG)
    @Size(min = 1, max = 15, message = ErrorMessage.PRODUCT_NAME_VALID_SIZE_MSG)
    @Pattern(
        regexp = RegularExpression.PRODUCT_NAME_CHAR_VALID_REGEX,
        message = ErrorMessage.PRODUCT_NAME_VALID_CHAR_MSG)
    @Pattern(
        regexp = RegularExpression.PRODUCT_NAME_FIND_KAKAO_REGEX,
        message = ErrorMessage.PRODUCT_NAME_VALID_KAKAO_MSG)
    String name,

    @NotNull
    long price,

    String imageUrl) {

}
