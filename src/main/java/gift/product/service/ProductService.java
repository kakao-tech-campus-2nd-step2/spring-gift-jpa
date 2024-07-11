package gift.product.service;

import gift.common.exception.ProductAlreadyExistsException;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
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

    public void createProduct(@Valid Product product) {
        checkForDuplicateProduct(product);
        productRepository.save(product);
    }

    public void updateProduct(Long id, @Valid Product product) {
        checkForDuplicateProduct(product);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
        existingProduct.setId(id);
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImageUrl(product.getImageUrl());

        productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
        productRepository.delete(existingProduct);
    }

    public void checkForDuplicateProduct(Product product) {
        List<Product> products = productRepository.findAll();
        for (Product p : products) {
            if (p.equals(product)) {
                throw new ProductAlreadyExistsException(product.getName());
            }
        }
    }
}
