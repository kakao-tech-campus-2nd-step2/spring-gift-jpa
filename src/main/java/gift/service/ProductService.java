package gift.service;

import gift.repository.ProductRepository;
import gift.vo.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));
    }

    public void addProduct(@Valid Product product) {
        productRepository.save(product);
    }

    public void updateProduct(@Valid Product product) {
        productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalArgumentException("수정하려는 상품을 찾을 수 없습니다."));
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("삭제하려는 상품을 찾을 수 없습니다."));
        productRepository.deleteById(id);
    }
}
