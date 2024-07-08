package gift.product;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record ProductDTO(
    @Length(min = 1, max = 15, message = "product name's length must be between 1 and 15")
    // 제품의 이름은 반드시 영어, 한글, 숫자, 그리고 특수문자 (, ), [, ], +, -, &, /, _ 만 혀용한다.
    @Pattern(regexp = "^[a-zA-Z0-9가-힣()\\[\\]+\\-&/_]*$", message = "product name must consist of English, Korean, numbers, and special symbols (, ), [, ], +, -, &, /, _")
    // 제품의 이름에 대소문자 구문없이 kakao, 카카오라는 이름이 들어가서는 안된다.
    @Pattern(regexp = "^(?i)(?!.*(kakao|카카오)).*$", message = "if you include 'kakao' in you product name, then you must be consult with your MD")
    String name,
    int price,
    String imageUrl) {

}
