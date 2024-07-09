package gift.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductDto(long id,
                      @NotEmpty(message = "상품 이름은 필수 입력값입니다.")
                      String name,
                      int price,
                      String imageUrl,
                      int amount) { }
