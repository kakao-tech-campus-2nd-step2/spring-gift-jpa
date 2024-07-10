package gift.service;

import gift.converter.ProductConverter;
import gift.dto.ProductDTO;
import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
            .map(ProductConverter::convertToDTO)
            .collect(Collectors.toList());
    }

    public Long addProduct(ProductDTO productDTO) {
        Product product = ProductConverter.convertToEntity(productDTO);
        productRepository.save(product);
        return product.getId();
    }

    public Optional<ProductDTO> findProductById(Long id) {
        return productRepository.findById(id)
            .map(ProductConverter::convertToDTO);
    }

    public void updateProduct(ProductDTO productDTO) {
        Product product = ProductConverter.convertToEntity(productDTO);
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}