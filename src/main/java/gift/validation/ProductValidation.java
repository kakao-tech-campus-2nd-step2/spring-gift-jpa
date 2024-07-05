package gift.validation;

import gift.dto.CreateProductDto;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ProductValidation {
    private static final String ALLOWED_SPECIAL_CHARACTERS = "()[]+\\-&/_.";

    public void validateProductDto(CreateProductDto productDto) {
        if (productDto.getName() == null || productDto.getDescription() == null || productDto.getPrice() == null || productDto.getImageUrl() == null) {
            throw new IllegalArgumentException("상품의 이름, 가격, 설명을 모두 입력해야합니다.");
        }
    }

    public void validateProductExists(Long productId, ProductRepository productRepository) {
        if (productRepository.findById(productId) == null) {
            throw new IllegalArgumentException("일치하는 상품이 없습니다.");
        }
    }

    public void validateProductName(String name) {

        if (name.length() > 15) {
            throw new IllegalArgumentException("상품 이름은 15자 이하여야 합니다.");
        }

        Pattern pattern = Pattern.compile("[^" + ALLOWED_SPECIAL_CHARACTERS + "a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            throw new IllegalArgumentException("상품 이름에는 허용된 특수 문자 (" + ALLOWED_SPECIAL_CHARACTERS + ") 만 사용할 수 있습니다.");
        }
    }
}
