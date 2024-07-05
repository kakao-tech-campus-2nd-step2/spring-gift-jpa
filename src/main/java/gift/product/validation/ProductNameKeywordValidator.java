package gift.product.validation;

import gift.product.domain.ProductName;
import gift.product.exception.ProductNameContainsException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class ProductNameKeywordValidator implements ConstraintValidator<ValidProductName, ProductName> {
    private static final List<String> KEY_WORDS = Arrays.asList("카카오", "kakao");

    @Override
    public boolean isValid(ProductName productName, ConstraintValidatorContext constraintValidatorContext) {
        if (KEY_WORDS.stream().anyMatch(productName.getName().replaceAll("\\s", "")::contains)) {
            throw new ProductNameContainsException();
        }

        return true;
    }
}
