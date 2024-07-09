package gift.service;

import gift.domain.Product;
import gift.exception.ProductAlreadyExistsException;
import gift.exception.ProductNotExistsException;
import gift.repository.ProductRepository;
import gift.controller.product.ProductResponse;
import gift.controller.product.ProductRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
            .map(ProductResponse::of)
            .toList();
    }

    public ProductResponse find(Long id) {
        Optional<Product> product =  productRepository.findById(id);
        product.orElseThrow(ProductNotExistsException::new);
        return ProductResponse.of(product.get());
    }

    public ProductResponse save(ProductRequest product) {
        productRepository.findByValues(product).ifPresent(p -> {
            throw new ProductAlreadyExistsException();
        });
        return ProductResponse.of(productRepository.save(product));
    }

    public ProductResponse update(Long id, ProductRequest product) {
        productRepository.findById(id).orElseThrow(ProductNotExistsException::new);
        return ProductResponse.of(productRepository.update(id, product));
    }

    public void delete(Long id) {
        productRepository.findById(id).orElseThrow(ProductNotExistsException::new);
        productRepository.delete(id);
    }
}
