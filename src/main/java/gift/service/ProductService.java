package gift.service;

import gift.domain.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import gift.request.ProductRequest;
import gift.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponse> getProducts(Pageable pageable) {
        List<ProductResponse> response = productRepository.findAll(pageable).stream()
                .map(Product::toDto)
                .toList();

        return new PageImpl<>(response, pageable, response.size());
    }

    public ProductResponse getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new)
                .toDto();
    }

    public void addProduct(ProductRequest request) {
        productRepository.save(request.toEntity());
    }

    @Transactional
    public void editProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        product.changeName(request.getName());
        product.changePrice(request.getPrice());
        product.changeImageUrl(request.getImageUrl());
    }

    public void removeProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.deleteById(product.getId());
    }

}
