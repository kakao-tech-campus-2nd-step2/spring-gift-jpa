package gift.service;

import gift.dto.ProductPageResponseDto;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Product;
import gift.entity.ProductName;
import gift.exception.BusinessException;
import gift.exception.ErrorCode;
import gift.mapper.ProductMapper;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto addProduct(ProductRequestDto productRequestDTO) {
        Product product = new Product(new ProductName(productRequestDTO.getName()), productRequestDTO.getPrice(), productRequestDTO.getImageUrl());
        Product createdProduct = productRepository.save(product);
        return ProductMapper.toProductResponseDTO(createdProduct);
    }

    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDTO) {
        Product existingProduct = getProductEntityById(id);
        existingProduct.update(
                new ProductName(productRequestDTO.getName()),
                productRequestDTO.getPrice(),
                productRequestDTO.getImageUrl());
        return ProductMapper.toProductResponseDTO(existingProduct);
    }

    public ProductPageResponseDto getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponseDto> productDTOs = productRepository.findAll(pageable).map(ProductMapper::toProductResponseDTO);
        return ProductPageResponseDto.fromPage(productDTOs);
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = getProductEntityById(id);
        return ProductMapper.toProductResponseDTO(product);
    }

    public Product getProductEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "ID: " + id));
    }

    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "ID: " + id);
        }
        productRepository.deleteById(id);
        return true;
    }

    public List<ProductResponseDto> getProductsByIds(List<Long> ids) {
        List<Product> products = productRepository.findAllById(ids);
        return products.stream()
                .map(ProductMapper::toProductResponseDTO)
                .collect(Collectors.toList());
    }
}
