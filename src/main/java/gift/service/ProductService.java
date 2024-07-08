package gift.service;

import static gift.util.Constants.INVALID_PRICE;
import static gift.util.Constants.PRODUCT_NOT_FOUND;

import gift.dto.product.ProductRequest;
import gift.dto.product.ProductResponse;
import gift.exception.product.InvalidProductPriceException;
import gift.exception.product.ProductNotFoundException;
import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 모든 상품 조회
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
            .map(ProductService::convertToDTO)
            .collect(Collectors.toList());
    }

    // ID로 상품 조회
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
            .map(ProductService::convertToDTO)
            .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + id));
    }

    // 상품 추가
    public ProductResponse addProduct(ProductRequest productDTO) {
        validatePrice(productDTO.price());
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.create(product);
        return convertToDTO(savedProduct);
    }

    // 상품 수정
    public ProductResponse updateProduct(Long id, ProductRequest productDTO) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + id));
        validatePrice(productDTO.price());
        product.update(productDTO.name(), productDTO.price(), productDTO.imageUrl());
        Product updatedProduct = productRepository.update(product);
        return convertToDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND + id);
        }
        productRepository.delete(id);
    }

    private static void validatePrice(int price) {
        if (price < 0) {
            throw new InvalidProductPriceException(INVALID_PRICE);
        }
    }

    // Mapper methods
    private static ProductResponse convertToDTO(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    private static Product convertToEntity(ProductRequest productDTO) {
        return new Product(productDTO.id(), productDTO.name(), productDTO.price(), productDTO.imageUrl());
    }
}
