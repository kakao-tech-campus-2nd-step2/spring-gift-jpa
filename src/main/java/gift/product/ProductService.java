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
        return productRepository.findAll();
    }

    public void addProduct(ProductDTO productDTO) {
        productRepository.save(
            new Product(
                -1,
                productDTO.name(),
                productDTO.price(),
                productDTO.imageUrl()
            )
        );
    }

    public void updateProduct(long id, ProductDTO productDTO) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product does not exist");
        }
        productRepository.save(
            new Product(
                id,
                productDTO.name(),
                productDTO.price(),
                productDTO.imageUrl()
            )
        );
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
