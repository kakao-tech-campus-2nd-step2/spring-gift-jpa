package gift.product.restapi.dto.request;

import gift.product.restapi.validator.NotContainingKaKao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductCreateRequest(
        @Size(min = 1, max = 15, message = "상품명은 1자 이상 15자 이하여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣()\\[\\]+\\-&/_ ]*$", message = "특수문자는 ()[]+-&/_ 만 허용됩니다.")
        @NotContainingKaKao
        String name,
        @NotNull Integer price,
        @NotBlank String imageUrl
) {
}
