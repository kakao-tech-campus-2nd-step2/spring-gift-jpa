package gift.service;

import gift.dto.ProductRequest;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("해당 id를 가지고있는 Product 객체가 없습니다."));
    }

    public Product saveProduct(ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImg());
        productRepository.save(product);
        return product;
    }

    public void updateProduct(Long id, ProductRequest productRequest) {
        Product product = new Product(id, productRequest.getName(), productRequest.getPrice(),
            productRequest.getImg());
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
