package gift.service;

import gift.dto.ProductRequestDTO;
import gift.dto.ProductsPageResponseDTO;
import gift.dto.ProductsResponseDTO;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public ProductsPageResponseDTO getAllProducts(Pageable pageable) {
        Page<Product> pages = productRepository.findAll(pageable);

        return new ProductsPageResponseDTO(pages.getContent(),
                                           pages.getNumber(),
                                           pages.getTotalPages());
    }


    public void updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        productRepository.findById(id).ifPresent(product -> {
            Product updatedProduct = new Product(product.getId(),
                    productRequestDTO.name(),
                    productRequestDTO.price(),
                    productRequestDTO.imageUrl());

            productRepository.save(updatedProduct);
        });
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
