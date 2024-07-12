package gift.service;

import gift.dto.ProductRequest;
import gift.model.Product;
import gift.exception.product.ProductNotFoundException;
import gift.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
  
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product makeProduct(ProductRequest request) {
        Product product = new Product(
                request.name(),
                request.price(),
                request.imageUrl()
        );
        productRepository.save(product);
        return product;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("해당 id의 상품이 존재하지 않습니다."));
        return product;
    }

    @Transactional
    public Product putProduct(ProductRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(request.id());

        if (optionalProduct.isPresent()) {
            Product updateProduct = optionalProduct.get().update(
                    request.name(),
                    request.price(),
                    request.imageUrl()
            );
            return updateProduct;
        }
        throw new ProductNotFoundException("수정하려는 해당 id의 상품이 존재하지 않습니다.");
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("삭제하려는 해당 id의 상품이 존재하지 않습니디."));
        productRepository.deleteById(id);
    }
}
