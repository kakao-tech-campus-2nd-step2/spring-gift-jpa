package gift.service;

import gift.model.Product;
import gift.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ProductService {

    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void createProduct(@Valid Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Long id, @Valid Product product) {
        if (productRepository.existsById(id)) {
<<<<<<< HEAD
            product.setId(id);
            productRepository.save(product);
=======
            product.setId(id); // Ensure the product ID is set
            productRepository.save(product); // save() will perform an update if the entity already exists
>>>>>>> 0efc70c (경북대 BE_김동윤 3주차 과제 (0, 1, 2단계) (#66))
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
