package gift.main.error;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductNameValidator implements ConstraintValidator<ValidProductName, String> {

    @Override
    public void initialize(ValidProductName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name.isBlank() || name.length() > 15) {
            throw new ProductError("상품의 이름은 1~15사이의 문자열이어야합니다.");
        }

        if (!name.matches("^[가-힣a-zA-Z0-9 ()\\[\\]\\+\\-&/_]*$")) {
            throw new ProductError("상품명에 사용할 수 있는 특수 기호는 ( ), [ ], +, -, &, /, _  입니다.");
        }

        if (name.contains("카카오")) {
            throw new ProductError("\"상품명에 \"카카오\"를 입력하려면 관리자에게 문의하세요");
        }

        return true;
    }
}
