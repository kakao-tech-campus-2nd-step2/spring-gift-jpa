package gift.service;

import gift.common.exception.EntityNotFoundException;
import gift.controller.dto.request.ProductRequest;
import gift.controller.dto.response.ProductResponse;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponse> findAllProductPaging(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponse::from);
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

    @Transactional
    public void updateById(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product with id " + id + " not found"));
        product.updateProduct(request.name(), request.price(), request.imageUrl());
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
