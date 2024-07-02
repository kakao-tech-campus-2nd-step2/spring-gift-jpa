package gift.main.handler;

import gift.main.dto.ProductDto;
import gift.main.dto.ProductRequest;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

public class ProductValidator {

    public static ProductDto isValidProductDto(ProductRequest productRequest) {
        @Pattern(regexp = "^[a-zA-Z0-9( \\[\\]\\+\\-&/\\_]{1,15}$",
                message = "상품 이름은 공백을 포함하여 최대 15자까지 입력 가능하며, 허용되는 특수 문자는 ( ), [ ], +, -, &, /, _ 입니다.")
        String validName = productRequest.name();
        if (validName.contains("카카오")) {
            throw new IllegalArgumentException("카카오라는 이름을 사용시에 관리자에게 문의하세요");
        }
        @PositiveOrZero(message="가격은 음수일수 없습니다.")
        int validPrice = productRequest.price();

        String validimageUrl = productRequest.imageUrl();

        ProductDto productDto = new ProductDto(validName, validPrice, validimageUrl);
        return productDto;
    }
}
