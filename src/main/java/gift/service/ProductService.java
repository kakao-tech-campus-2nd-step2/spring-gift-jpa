package gift.service;

import gift.domain.Product;
import gift.repository.ProductJpaRepository;
import gift.web.dto.request.product.CreateProductRequest;
import gift.web.dto.request.product.UpdateProductRequest;
import gift.web.dto.response.product.CreateProductResponse;
import gift.web.dto.response.product.ReadAllProductsResponse;
import gift.web.dto.response.product.ReadProductResponse;
import gift.web.dto.response.product.UpdateProductResponse;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductJpaRepository productJpaRepository;

    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Transactional
    public CreateProductResponse createProduct(CreateProductRequest request) {
        Product product = request.toEntity();
        return CreateProductResponse.fromEntity(productJpaRepository.save(product));
    }

    public ReadProductResponse searchProduct(Long id) {
        Product product = productJpaRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);
        return ReadProductResponse.fromEntity(product);
    }

    public ReadAllProductsResponse readAllProducts() {
        List<ReadProductResponse> products = productJpaRepository.findAll()
            .stream()
            .map(ReadProductResponse::fromEntity)
            .toList();
        return ReadAllProductsResponse.from(products);
    }

    @Transactional
    public UpdateProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productJpaRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);

        product.update(request);
        return UpdateProductResponse.from(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productJpaRepository.deleteById(id);
    }
}
