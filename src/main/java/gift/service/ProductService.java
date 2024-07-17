package gift.service;

import gift.domain.product.Product;
import gift.domain.product.ProductRepository;
import gift.dto.ProductChangeRequestDto;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.exception.CustomException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static gift.exception.ErrorCode.INVALID_PRODUCT;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void addProduct(ProductRequestDto requestDto) {
        Product product = new Product(requestDto.getName(), requestDto.getPrice(), requestDto.getImgUrl());
        productRepository.save(product);
    }

    public List<ProductResponseDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .stream()
                .map(ProductResponseDto::new)
                .toList();
    }

    public ProductResponseDto findProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(INVALID_PRODUCT));
        return new ProductResponseDto(product);
    }

    @Transactional
    public ProductResponseDto editProduct(Long id, ProductChangeRequestDto request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(INVALID_PRODUCT));
        product.update(request.getName(), request.getPrice(), request.getImgUrl());
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        checkProductValidation(id);
        productRepository.deleteById(id);
    }

    private void checkProductValidation(Long id) {
        if (!productRepository.existsById(id)) {
            throw new CustomException(INVALID_PRODUCT);
        }
    }
}
