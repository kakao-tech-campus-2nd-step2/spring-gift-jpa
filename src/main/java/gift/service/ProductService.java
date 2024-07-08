package gift.service;

import gift.dto.ProductRequestDTO;
import gift.dto.ProductsResponseDTO;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequestDTO productRequestDTO) {
        productRepository.insertToTable(productRequestDTO);
    }

    public ProductsResponseDTO getAllProducts() {
        return new ProductsResponseDTO(productRepository.selectAllProducts());
    }


    public void updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        productRepository.updateToTable(id, productRequestDTO);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteToTable(id);
    }
}
