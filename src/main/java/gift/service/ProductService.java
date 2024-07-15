package gift.service;

import gift.repository.ProductRepository;
import gift.vo.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 페이지네이션 적용된 모든 상품 가져오는 메소드
     * @return Page<Product></Product>
     */
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        System.out.println("service: pageNumber"+ pageNumber);
        return productRepository.findAll(pageable);
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
