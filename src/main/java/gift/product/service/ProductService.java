package gift.product.service;

import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import gift.product.validator.ProductNameValidator;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductNameValidator productNameValidator;

    public ProductService(ProductRepository productRepository, ProductNameValidator productNameValidator) {
        this.productRepository = productRepository;
        this.productNameValidator = productNameValidator;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    public Product save(@Valid Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    private void validateProduct(Product product) {
        BindingResult result = new BeanPropertyBindingResult(product, "product");
        productNameValidator.validate(product, result);
        if (result.hasErrors()) {
            throw new IllegalArgumentException(result.getFieldError().getDefaultMessage());
        }
    }

}
