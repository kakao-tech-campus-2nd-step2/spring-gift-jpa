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
        return productRepository.findProductsAll();
    }

    public Product findProductsById(Long id) {
        return productRepository.findProductsById(id);
    }

    public void saveProduct(ProductDTO productDTO) {
        productRepository.saveProduct(toEntity(productDTO, null));
    }

    public void updateProduct(ProductDTO productDTO, Long id) {
        productRepository.updateProduct(toEntity(productDTO, id), id);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProduct(id);
    }

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(product.name(), String.valueOf(product.price()), product.imageUrl());
    }

    private static Product toEntity(ProductDTO productDTO, Long id) {
        return new Product(id, productDTO.name(), productDTO.price(), productDTO.imageUrl());
    }
}
