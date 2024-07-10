package gift.product.service;

import gift.product.application.dto.request.ProductRequest;
import gift.product.application.dto.response.ProductResponse;
import gift.product.domain.Product;
import gift.product.exception.ProductNotFoundException;
import gift.product.persistence.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProduct(ProductRequest productRequest) {
        Product product = productRequest.toModel();
        productRepository.save(product);
    }

    public void modifyProduct(final Long id, ProductRequest productRequest) {
        productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));

        Product newProduct = productRequest.toModel();
        productRepository.save(newProduct);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductDetails(final Long id) {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));

        return ProductResponse.fromModel(foundProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts() {
        List<Product> foundProducts = productRepository.findAll();

        return foundProducts.stream()
                .map(ProductResponse::fromModel)
                .toList();
    }

    @Transactional
    public void deleteProduct(final Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));
        productRepository.delete(product);
    }
}
