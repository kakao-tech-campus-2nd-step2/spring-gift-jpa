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
        Product product = new Product(productRequestDTO.name(),
                                      productRequestDTO.price(),
                                      productRequestDTO.imageUrl());

        productRepository.save(product);
    }

    public ProductsResponseDTO getAllProducts() {
        return new ProductsResponseDTO(productRepository.findAll());
    }


    public void updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        productRepository.findById(id).ifPresent(product -> {
            product.setName(productRequestDTO.name());
            product.setPrice(productRequestDTO.price());
            product.setImageUrl(productRequestDTO.imageUrl());
            productRepository.save(product);
        });
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
