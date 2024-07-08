package gift.service;

import gift.controller.product.dto.ProductRequest;
import gift.controller.product.dto.ProductResponse;
import gift.global.dto.PageResponse;
import gift.repository.ProductJpaRepository;
import gift.validate.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductJpaRepository productJpaRepository;

    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Transactional(readOnly = true)
    public ProductResponse.Info getProduct(Long id) {
        var product = productJpaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        return ProductResponse.Info.from(product);
    }

    @Transactional
    public void createProduct(ProductRequest.Register request) {
        productJpaRepository.save(request.toEntity());
    }

    @Transactional
    public void updateProduct(Long id, ProductRequest.Update request) {
        var product = productJpaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        product.update(request.name(), request.price(), request.imageUrl());
        productJpaRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productJpaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PageResponse<ProductResponse.Info> getProductsPaging(int page, int size) {
        var productPage = productJpaRepository.findAllByOrderByIdDesc(
            PageRequest.of(page, size));
        var content = productPage.getContent().stream()
            .map(ProductResponse.Info::from)
            .toList();
        return new PageResponse<>(content, productPage.getNumber(),
            productPage.getSize(), productPage.getTotalPages(),
            (int) productPage.getTotalElements());
    }
}
