package gift.service;

import gift.dto.product.AddProductRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.UpdateProductRequest;
import gift.entity.Product;
import gift.exception.product.ProductNotFoundException;
import gift.repository.ProductRepository;
import gift.util.mapper.ProductMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return checkProductExist(id);
    }

    public ProductResponse addProduct(AddProductRequest request) {
        return ProductMapper.toResponse(productRepository.save(ProductMapper.toProduct(request)));
    }

    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = checkProductExist(id);
        ProductMapper.updateProduct(product, request);

        return ProductMapper.toResponse(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        checkProductExist(id);
        productRepository.deleteById(id);
    }

    public Product checkProductExist(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("해당 ID의 상품을 찾을 수 없습니다."));
    }
}
