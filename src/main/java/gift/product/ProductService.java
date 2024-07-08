package gift.product;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void addProduct(ProductDTO productDTO) {
        productRepository.addProduct(
            new Product(
                -1,
                productDTO.name(),
                productDTO.price(),
                productDTO.imageUrl()
            )
        );
    }

    public void updateProduct(long id, ProductDTO productDTO) {
        productRepository.updateProduct(
            new Product(
                id,
                productDTO.name(),
                productDTO.price(),
                productDTO.imageUrl()
            )
        );
    }

    public void deleteProduct(long id) {
        productRepository.deleteProduct(id);
    }
}
