package gift.service;

import gift.common.exception.EntityNotFoundException;
import gift.controller.dto.request.ProductRequest;
import gift.controller.dto.response.ProductResponse;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAllByOrderByCreatedAtAsc().stream()
                .map(ProductResponse::from)
                .toList();
    }

    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product with id " + id + " not found"));
        return ProductResponse.from(product);
    }

    public Long save(ProductRequest request) {
        Product product = new Product(request.name(), request.price(), request.imageUrl());
        return productRepository.save(product).getId();
    }

    public void updateById(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product with id " + id + " not found"));
        product.updateProduct(request.name(), request.price(), request.imageUrl());
        productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
