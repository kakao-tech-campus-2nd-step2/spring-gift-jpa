package gift.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RequestProduct (
        @Pattern(
                regexp = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣 ()\\[\\]+\\-\\&/_]*$",
                message = "상품 이름에는 허용된 특수 문자만 포함될 수 있습니다: (), [], +, -, &, /, _"
        )
        @Pattern(regexp = "^(?!.*카카오).*$",
                message = "상품 이름에 '카카오'가 포함된 문구는 담당 MD와 협의가 필요합니다.")
        @NotNull(message = "상품 이름은 필수입니다")
        @Size(max = 15, message = "상품 이름은 최대 15자 입니다.")
    String name,
    int price,
    String imageUrl
){}


