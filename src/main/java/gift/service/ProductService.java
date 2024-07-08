package gift.service;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Product;
import gift.entity.ProductName;
import gift.exception.BusinessException;
import gift.exception.ErrorCode;
import gift.mapper.ProductMapper;
import gift.repository.ProductRepository;
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
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "ID: " + id));

        existingProduct.update(
                new ProductName(productRequestDTO.getName()),
                productRequestDTO.getPrice(),
                productRequestDTO.getImageUrl());
        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.toProductResponseDTO(updatedProduct);
    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::toProductResponseDTO)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "ID: " + id));
        return ProductMapper.toProductResponseDTO(product);
    }

    public boolean deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "ID: " + id));
        productRepository.delete(product);
        return true;
    }
}
