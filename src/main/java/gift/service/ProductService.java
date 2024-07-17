package gift.service;

import gift.domain.Product;
import gift.repository.ProductRepository;
import gift.web.dto.request.product.CreateProductRequest;
import gift.web.dto.request.product.UpdateProductRequest;
import gift.web.dto.response.product.CreateProductResponse;
import gift.web.dto.response.product.ReadAllProductsResponse;
import gift.web.dto.response.product.ReadProductResponse;
import gift.web.dto.response.product.UpdateProductResponse;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public CreateProductResponse createProduct(CreateProductRequest request) {
        Product product = request.toEntity();
        return CreateProductResponse.fromEntity(productRepository.save(product));
    }

    public ReadProductResponse searchProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);
        return ReadProductResponse.fromEntity(product);
    }

    public ReadAllProductsResponse readAllProducts() {
        List<ReadProductResponse> products = productRepository.findAll()
            .stream()
            .map(ReadProductResponse::fromEntity)
            .toList();
        return ReadAllProductsResponse.from(products);
    }

    public ReadAllProductsResponse readAllProducts(Pageable pageable) {
        List<ReadProductResponse> products = productRepository.findAll(pageable)
            .stream()
            .map(ReadProductResponse::fromEntity)
            .toList();
        return ReadAllProductsResponse.from(products);
    }

    @Transactional
    public UpdateProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);

        product.update(request.toEntity());
        return UpdateProductResponse.from(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);
        productRepository.delete(product);
    }
}
