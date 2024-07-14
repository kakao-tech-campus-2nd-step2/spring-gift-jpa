package gift.product;

import static gift.exception.ErrorMessage.PRODUCT_NAME_ALLOWED_CHARACTER;
import static gift.exception.ErrorMessage.PRODUCT_NAME_KAKAO_STRING;
import static gift.exception.ErrorMessage.PRODUCT_NAME_LENGTH;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record ProductDTO(
    @Length(min = 1, max = 15, message = PRODUCT_NAME_LENGTH)
    // 제품의 이름은 반드시 영어, 한글, 숫자, 그리고 특수문자 (, ), [, ], +, -, &, /, _ 만 혀용한다.
    @Pattern(regexp = "^[a-zA-Z0-9가-힣()\\[\\]+\\-&/_]*$", message = PRODUCT_NAME_ALLOWED_CHARACTER)
    // 제품의 이름에 대소문자 구문없이 kakao, 카카오라는 이름이 들어가서는 안된다.
    @Pattern(regexp = "^(?i)(?!.*(kakao|카카오)).*$", message = PRODUCT_NAME_KAKAO_STRING)
    String name,

    int price,
    String imageUrl) {

    public Product toEntity() {
        return new Product(-1, name, price, imageUrl);
    }

    public Product toEntity(long id) {
        return new Product(id, name, price, imageUrl);
    }
}
