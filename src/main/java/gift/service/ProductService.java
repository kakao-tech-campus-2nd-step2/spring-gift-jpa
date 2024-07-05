package gift.service;

import gift.exception.InvalidProductDataException;
import gift.exception.ProductAlreadyExistsException;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        checkValidProduct(product);
        checkForDuplicateProduct(product);
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        checkValidProduct(product);
        checkForDuplicateProduct(product);
        return productRepository.update(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void checkValidProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new InvalidProductDataException("커피 이름");
        }
        if (product.getPrice() == null || product.getPrice() < 0 || product.getPrice() == 0){
            throw new InvalidProductDataException("커피 가격");
        }
        if (product.getImageurl() == null || product.getImageurl().isEmpty()) {
            throw new InvalidProductDataException("이미지 URL");
        }
    }
    public void checkForDuplicateProduct(Product product) {
        List<Product> products = productRepository.findAll();
        for (Product p : products) {
            if (p.equalProduct(product)) {
                throw new ProductAlreadyExistsException(product.getName());
            }
        }
    }
}
