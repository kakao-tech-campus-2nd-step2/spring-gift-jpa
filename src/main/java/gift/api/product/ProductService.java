package gift.api.product;

import gift.global.exception.NoSuchIdException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(int page, int size, String criterion, String direction) {
        Pageable pageRequest = PageRequest.of(page, size,
            Sort.by(Direction.fromOptionalString(direction).orElse(Direction.ASC), criterion));
        Page<Product> allProducts = productRepository.findAll(pageRequest);
        return allProducts.hasContent() ? allProducts.getContent() : Collections.emptyList();
    }

    public Long add(ProductRequest productRequest) {
        Product product = new Product(
            productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
        return productRepository.save(product).getId();
    }

    @Transactional
    public void update(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchIdException("product"));
        product.update(productRequest.getName(), productRequest.getPrice(),
                productRequest.getImageUrl());
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
