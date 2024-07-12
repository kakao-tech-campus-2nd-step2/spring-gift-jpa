package gift.service;

import gift.dto.product.AddProductRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.UpdateProductRequest;
import gift.entity.Product;
import gift.exception.product.ProductNotFoundException;
import gift.repository.ProductRepository;
import gift.util.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductMapper::toResponse);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("해당 ID의 상품을 찾을 수 없습니다."));
    }

    public Long addProduct(AddProductRequest request) {
        return productRepository.save(ProductMapper.toProduct(request)).getId();
    }

    public void updateProduct(Long id, UpdateProductRequest request) {
        Product product = getProductById(id);
        ProductMapper.updateProduct(product, request);
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        checkProductExist(id);
        productRepository.deleteById(id);
    }

    public void checkProductExist(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("해당 ID의 상품을 찾을 수 없습니다.");
        }
    }
}
