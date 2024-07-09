package gift.service;

import gift.controller.product.dto.ProductRequest.ProductRegisterRequest;
import gift.controller.product.dto.ProductRequest.ProductUpdateRequest;
import gift.controller.product.dto.ProductResponse.ProductInfoResponse;
import gift.global.dto.PageResponse;
import gift.repository.ProductJpaRepository;
import gift.validate.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductJpaRepository productJpaRepository;

    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    public ProductInfoResponse getProduct(Long id) {
        var product = productJpaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        return ProductInfoResponse.from(product);
    }

    public void createProduct(ProductRegisterRequest request) {
        productJpaRepository.save(request.toEntity());
    }

    public void updateProduct(Long id, ProductUpdateRequest request) {
        var product = productJpaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        product.update(request.name(), request.price(), request.imageUrl());
        productJpaRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productJpaRepository.deleteById(id);
    }

    public PageResponse<ProductInfoResponse> getProductsPaging(int page, int size) {
        var productPage = productJpaRepository.findAllByOrderByIdDesc(
            PageRequest.of(page, size));
        var content = productPage.getContent().stream()
            .map(ProductInfoResponse::from)
            .toList();
        return new PageResponse<>(content, productPage.getNumber(),
            productPage.getSize(), productPage.getTotalPages(),
            (int) productPage.getTotalElements());
    }
}
