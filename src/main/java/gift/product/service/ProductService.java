package gift.product.service;

import gift.product.repository.ProductRepository;
import gift.product.model.Product;
import gift.product.validation.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductValidation productValidation;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductValidation productValidation) {
        this.productRepository = productRepository;
        this.productValidation = productValidation;
    }

    public ResponseEntity<String> registerProduct(Product product) {
        System.out.println(product.getId()+" "+product.getName()+" "+ product.getPrice()+" "+product.getImageUrl());
        productValidation.registerValidation(product);
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product registered successfully");
    }

    public ResponseEntity<String> updateProduct(Long id, Product product) {
        productValidation.updateValidation(id, product);
        productRepository.save(new Product(id, product.getName(), product.getPrice(), product.getImageUrl()));
        return ResponseEntity.status(HttpStatus.CREATED).body("Product update successfully");
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Collection<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Collection<Product> searchProducts(String keyword) {
        return productRepository.findByName(keyword);
    }

    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }
}
