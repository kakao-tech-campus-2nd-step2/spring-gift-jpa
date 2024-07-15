package gift.service;

import gift.domain.model.dto.ProductAddRequestDto;
import gift.domain.model.dto.ProductResponseDto;
import gift.domain.model.dto.ProductUpdateRequestDto;
import gift.domain.model.enums.ProductSortBy;
import gift.domain.repository.ProductRepository;
import gift.domain.model.entity.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private static final int PAGE_SIZE = 10;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품 ID입니다."));
        return convertToResponseDto(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getAllProducts(int page, ProductSortBy sortBy) {
        Sort sort = sortBy.getSort();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);

        Page<Product> productPage = productRepository.findAllProducts(pageable);

        return productPage.map(this::convertToResponseDto);
    }

    @Transactional
    public ProductResponseDto addProduct(@Valid ProductAddRequestDto productAddRequestDto) {
        validateProductName(productAddRequestDto.getName());
        validateDuplicateProduct(productAddRequestDto.getName());
        Product product = convertAddRequestToEntity(productAddRequestDto);
        Product savedProduct = productRepository.save(product);
        return convertToResponseDto(savedProduct);
    }

    private void validateDuplicateProductId(Long id) {
        if (productRepository.existsById(id)) {
            throw new IllegalArgumentException("이미 존재하는 상품 ID입니다.");
        }
    }

    private void validateDuplicateProduct(String name) {
        if (productRepository.existsByName(name)) {
            throw new IllegalArgumentException("이미 존재하는 상품명입니다.");
        }
    }

    private void validateProductName(String name) {
        if (name.contains("카카오")) {
            throw new IllegalArgumentException("'카카오'가 포함된 상품명은 담당 MD와 협의가 필요합니다.");
        }
    }

    @Transactional
    public ProductResponseDto updateProduct(@Valid ProductUpdateRequestDto productAddRequestDto) {
        Product product = productRepository.findById(productAddRequestDto.getId())
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
        if (productRepository.existsByName(productAddRequestDto.getName())) {
            throw new IllegalArgumentException("이미 존재하는 상품명입니다.");
        }
        product.update(productAddRequestDto.getName(), productAddRequestDto.getPrice(),
            productAddRequestDto.getImageUrl());
        return convertToResponseDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        validateExistProductId((id));
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public void validateExistProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new NoSuchElementException("존재하지 않는 상품입니다.");
        }
    }

    private Product convertAddRequestToEntity(ProductAddRequestDto productAddRequestDto) {
        return new Product(
            productAddRequestDto.getName(),
            productAddRequestDto.getPrice(),
            productAddRequestDto.getImageUrl()
        );
    }

    private ProductResponseDto convertToResponseDto(Product product) {
        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        );
    }

    protected Product convertResponseDtoToEntity(ProductResponseDto productResponseDto) {
        return new Product(
            productResponseDto.getId(),
            productResponseDto.getName(),
            productResponseDto.getPrice(),
            productResponseDto.getImageUrl()
        );
    }
}