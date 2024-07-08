package gift.service;

import gift.dto.product.AddProductRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.UpdateProductRequest;
import gift.exception.product.ProductNotFoundException;
import gift.entity.Product;
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
        checkProductExist(id);
        return productRepository.findById(id);
    }

    public ProductResponse addProduct(AddProductRequest request) {
        return ProductMapper.toResponse(productRepository.insert(ProductMapper.toProduct(request)));
    }

    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        checkProductExist(id);
        Product product = ProductMapper.toProduct(id, request);
        return ProductMapper.toResponse(productRepository.update(product));
    }

    public void deleteProduct(Long id) {
        checkProductExist(id);
        productRepository.delete(id);
    }

    public void checkProductExist(Long id) {
        Product product = productRepository.findById(id);

        if(product == null) {
            throw new ProductNotFoundException("해당 ID의 상품을 찾을 수 없습니다.");
        }
    }
}
