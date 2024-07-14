package gift.product;

import static gift.exception.ErrorMessage.PRODUCT_NOT_FOUND;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public void addProduct(ProductDTO productDTO) {
        productRepository.save(productDTO.toEntity());
    }

    public void updateProduct(long id, ProductDTO productDTO) {
        productRepository.findById(id)
            .ifPresentOrElse(
                e -> productRepository.save(productDTO.toEntity(id)),
                () -> {
                    throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
                }
            );
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
