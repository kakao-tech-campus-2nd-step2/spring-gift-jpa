package gift.dto.product;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductRequest(
    Long id,

    @Size(min = 1, max = 15, message = "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다.")
    @Pattern(
        regexp = "^[a-zA-Z0-9ㄱ-ㅎ가-힣\\(\\)\\[\\]\\+\\-\\&\\/\\_ ]*$",
        message = "상품 이름에는 다음 특수 문자의 사용만 허용됩니다: ( ), [ ], +, -, &, /, _"
    )
    @Pattern(
        regexp = "^(?!.*카카오).*$",
        message = "\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다."
    )
    String name,

    int price,

    String imageUrl
) { }
