package gift.service;

import gift.domain.Product;
import gift.dto.ProductRequest;
import gift.entity.ProductEntity;
import gift.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product find(Long id) {
        ProductEntity productEntity = productRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));
        return entityToDomain(productEntity);
    }

    public List<Product> findAll() {
        return productRepository.findAll()
            .stream()
            .map(this::entityToDomain)
            .collect(Collectors.toList());
    }

    public Product createProduct(ProductRequest productRequest) {
        ProductEntity productEntity = productRepository.save(productRequest.toProductEntity());
        return entityToDomain(productEntity);
    }

    public Product updateProduct(Long id, ProductRequest productRequest) {
        ProductEntity productEntity = productRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));

        productEntity.update(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());

        return entityToDomain(productRepository.save(productEntity));

    }

    public void deleteProduct(Long id) {
        ProductEntity productEntity = productRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));
        productRepository.delete(productEntity);
    }

    private Product entityToDomain(ProductEntity productEntity){
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getPrice(),productEntity.getImageUrl());
    }

    private ProductEntity dtoToEntity(ProductRequest productRequest){
        return new ProductEntity(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
    }
}
