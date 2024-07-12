package gift.product.application;

import gift.product.domain.CreateProductRequestDTO;
import gift.product.domain.Product;
import gift.product.exception.ProductException;
import gift.product.infra.ProductRepository;
import gift.util.ErrorCode;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private static final int MAX_PRODUCT_NAME_LENGTH = 15;
    private static final String RESERVED_KEYWORD = "카카오";

    @Transactional
    public Long saveProduct(CreateProductRequestDTO createProductRequestDTO) {
        Product product = new Product(createProductRequestDTO.getName(), createProductRequestDTO.getPrice(),
            createProductRequestDTO.getImageUrl());
        validateProduct(product);
        return productRepository.save(product).getId();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, String name, Double price, String imageUrl) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setName(name);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
        productRepository.save(product);
    }

    private void validateProduct(Product product) {
        validateName(product.getName());
        validatePrice(product.getPrice());
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new ProductException(ErrorCode.INVALID_NAME);
        }
        if (name.length() > MAX_PRODUCT_NAME_LENGTH) {
            throw new ProductException(ErrorCode.NAME_TOO_LONG);
        }

        if (name.contains(RESERVED_KEYWORD)) {
            throw new ProductException(ErrorCode.NAME_HAS_RESTRICTED_WORD);
        }
    }

    private void validatePrice(Double price) {
        if (price == null) {
            throw new ProductException(ErrorCode.INVALID_PRICE);
        }
        if (price < 0) {
            throw new ProductException(ErrorCode.NEGATIVE_PRICE);
        }
    }

    public Optional<Product> getProductByName(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProduct() {
        return productRepository.findAll();
    }

}
