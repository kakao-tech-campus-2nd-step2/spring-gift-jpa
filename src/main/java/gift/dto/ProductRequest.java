package gift.dto;

import gift.validation.KakaoNotAllowed;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public record ProductRequest(
        @NotBlank(message = "상품 이름은 공백일 수 없습니다.")
        @Size(max = 15, message = "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다.")
        @Pattern(regexp = "^[a-zA-Z0-9 ()\\[\\]+\\-&/_\\uAC00-\\uD7A3\\u3131-\\u3163]*$", message = "상품 이름이 유효하지 않은 문자를 포함하고 있습니다.")
        @KakaoNotAllowed(message = "\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.")
        String name,

        @Min(value = 0, message = "가격은 음수일 수 없습니다.")
        int price,

        String imageUrl) {
    public ProductRequest {
        Objects.requireNonNull(name, "상품 이름은 공백일 수 없습니다.");
        Objects.requireNonNull(imageUrl, "이미지 URL은 공백일 수 없습니다.");
    }
}
