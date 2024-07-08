package gift.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductDto(long id,
                      @NotEmpty(message = "상품 이름은 필수 입력값입니다.")
                      @Size(max = 15, message = "상품 이름은 최대 15자까지(공백 포함) 입력 가능합니다.")
                      @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s()\\[\\]+\\-&/_]*$", message = "상품 이름에 사용할 수 있는 특수 문자는 ( ), [ ], +, -, &, /, _ 입니다.")
                      @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.")
                      String name,
                      int price,
                      String imageUrl,
                      int amount) { }
