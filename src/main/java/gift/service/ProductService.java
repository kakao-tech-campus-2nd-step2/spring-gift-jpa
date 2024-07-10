package gift.service;

import gift.model.product.Product;
import gift.model.product.ProductRequest;
import gift.model.product.ProductResponse;
import gift.repository.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return ProductResponse.from(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productRequest.toEntity();
        return ProductResponse.from(productRepository.save(product));
    }

    public ProductResponse updateProduct(Long id, ProductRequest updatedProduct) {
        Product product = productRepository.findById(id).orElseThrow();
        product.update(updatedProduct);
        return ProductResponse.from(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}