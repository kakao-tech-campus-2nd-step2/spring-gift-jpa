package gift.service;

import gift.dto.ProductResponseDto;
import gift.entity.Product;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository=productRepository;
    }

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
            .map(this::productToDto)
            .collect(Collectors.toList());
    }
    private ProductResponseDto productToDto(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

//    public List<Product> findAll() {
//        return productRepository.findAll();
//    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Long addProduct(Product product) {
        if(productRepository.existsByName(product.getName())) {
            return -1L;
        }
        return productRepository.save(product).getId();
    }

    public Long updateProduct(Product product) {
        if(!productRepository.existsById(product.getId())) {
            return -1L;
        }
        return productRepository.save(product).getId();
    }

    public Long deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            return -1L;
        }
        productRepository.deleteById(id);
        return id;
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}
