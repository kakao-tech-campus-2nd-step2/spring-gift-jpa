package gift.util;

import gift.model.Product;
import gift.repository.ProductRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductIdValidator implements ConstraintValidator<ProductIdConstraint, Long> {

    private final ProductRepository productRepository;

    public ProductIdValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void initialize(ProductIdConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long productId, ConstraintValidatorContext cxt) {
        Product product = productRepository.findById(productId);
        if (product == null) return returnValidationResult("Such product doesn't exist", cxt);
        return true;
    }

    private static boolean returnValidationResult(String errorMsg, ConstraintValidatorContext cxt) {
        cxt.disableDefaultConstraintViolation();
        cxt.buildConstraintViolationWithTemplate(errorMsg)
                .addConstraintViolation();
        return false;
    }
}
