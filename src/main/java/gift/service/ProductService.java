package gift.service;

import gift.domain.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        if (!isExist(id)) {
            return createProduct(product);
        }
        Product updatedProduct = new Product.ProductBuilder()
            .id(id)
            .name(product.getName())
            .price(product.getPrice())
            .imageUrl(product.getImageUrl())
            .description(product.getDescription())
            .build();
        return productRepository.save(updatedProduct);
    }

    public Long deleteProduct(Long id) {
        if (!isExist(id)) {
            return -1L;
        }
        productRepository.deleteById(id);
        return id;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    private boolean isExist(Long id) {
        return productRepository.existsById(id);
    }
}
