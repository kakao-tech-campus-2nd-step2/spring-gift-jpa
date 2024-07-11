package gift.product;

import static gift.exception.ErrorMessage.PRODUCT_NOT_FOUND;

import java.util.List;
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

    public void addProduct(ProductDTO productDTO) {
        productRepository.save(Product.fromProductDTO(productDTO));
    }

    public void updateProduct(long id, ProductDTO productDTO) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
        }

        productRepository.save(Product.fromProductIdAndProductDTO(id, productDTO));
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
