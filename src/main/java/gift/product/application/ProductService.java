package gift.product.application;

import gift.product.domain.Product;
import gift.product.exception.ProductException;
import gift.product.infra.ProductRepository;
import gift.product.presentation.ProductManageController.CreateProductRequestDTO;
import gift.util.ErrorCode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    private static final int MAX_PRODUCT_NAME_LENGTH = 15;
    private static final String RESERVED_KEYWORD = "카카오";


    public Long addProduct(CreateProductRequestDTO createProductRequestDTO) {
        Product product = new Product(createProductRequestDTO.getName(), createProductRequestDTO.getPrice(),
            createProductRequestDTO.getImageUrl());
        validateProduct(product);
        return productRepository.addProduct(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProduct(id);
    }

    public void updateProduct(Long id, String name, Double price, String imageUrl) {
        Product product = new Product(name, price, imageUrl);
        productRepository.updateProduct(id, product);
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

    public Product getProductByName(Long id) {
        return productRepository.getProductById(id);
    }

    public List<Product> getProduct() {
        return productRepository.getProducts();
    }

}
