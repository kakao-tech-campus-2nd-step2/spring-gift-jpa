package gift.service;

import gift.model.Product;
import gift.dto.ProductDTO;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductsById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void saveProduct(ProductDTO productDTO) {
        productRepository.save(toEntity(productDTO, null));
    }

    public void updateProduct(ProductDTO productDTO, Long id) {
        productRepository.save(toEntity(productDTO, id));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getName(), String.valueOf(product.getPrice()), product.getImageUrl());
    }

    private static Product toEntity(ProductDTO productDTO, Long id) {
        return new Product(id, productDTO.name(), productDTO.price(), productDTO.imageUrl());
    }
}
