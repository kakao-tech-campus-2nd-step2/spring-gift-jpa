package gift.Valid;

import gift.Model.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NameValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        if (product.name().contains("카카오")) {
            errors.rejectValue("name", "name.invalid", "이름에 '카카오'가 포함될 수 없습니다.");
        }
    }
}
