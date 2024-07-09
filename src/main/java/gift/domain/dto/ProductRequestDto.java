package gift.domain.dto;

import gift.domain.entity.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductRequestDto(
    @NotNull
    @Size(min = 1, max = 15,
        message = "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[\\w\\s\\[\\]()\\+\\-&/_가-힣]*$",
        message = "상품 이름에 (), [], +, -, &, /, _ 이외의 특수 문자는 사용할 수 없습니다.")
    @Pattern(regexp = "^(?!.*카카오).*$",
        message = "상품 이름에 '카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.")
    String name,
    @NotNull
    Long price,
    @NotNull
    String imageUrl) {

    public static Product toEntity(Long id, ProductRequestDto requestDto) {
        return new Product(id, requestDto.name(), requestDto.price(), requestDto.imageUrl());
    }
}
