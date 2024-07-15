package gift.service;

import gift.controller.product.dto.ProductRequest;
import gift.controller.product.dto.ProductResponse;
import gift.global.dto.PageResponse;
import gift.model.product.Product;
import gift.global.validate.NotFoundException;
import gift.model.product.SearchType;
import gift.repository.product.ProductRepository;
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

    @Transactional(readOnly = true)
    public ProductResponse.Info getProduct(Long id) {
        var product = productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        return ProductResponse.Info.from(product);
    }

    //@Transactional
    public void createProduct(ProductRequest.Register request) {
        productRepository.save(request.toEntity());
    }

    @Transactional
    public void updateProduct(Long id, ProductRequest.Update request) {
        var product = productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        product.update(request.name(), request.price(), request.imageUrl());
    }

    //@Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PageResponse<ProductResponse.Info> getProductsPaging(
        SearchType searchType,
        String searchValue,
        Pageable pageable
    ) {

        Page<Product> productPage = switch (searchType) {
            case NAME -> productRepository.findByNameContaining(searchValue, pageable);
            case PRICE -> productRepository.findAllOrderByPrice(pageable);
            default -> productRepository.findAll(pageable);
        };

        return PageResponse.from(productPage, ProductResponse.Info::from);
    }
}

