package gift.domain.product.service;

import gift.domain.product.dto.ProductRequest;
import gift.domain.product.dto.ProductResponse;
import gift.domain.product.entity.Product;
import gift.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse getProduct(Long id) {
        Product product = productRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));
        return entityToDto(product);
    }

    public List<ProductResponse> getAllProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findAll(pageable)
            .stream()
            .map(this::entityToDto)
            .toList();
    }

    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = productRepository.save(dtoToEntity(productRequest));
        return entityToDto(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));

        product.update(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl());

        return entityToDto(productRepository.save(product));

    }

    public void deleteProduct(Long id) {
        Product product = productRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));
        productRepository.delete(product);
    }

    private ProductResponse entityToDto(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl());
    }

    private Product dtoToEntity(ProductRequest productRequest) {
        return new Product(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl());
    }

}
