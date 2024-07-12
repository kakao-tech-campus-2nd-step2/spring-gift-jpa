package gift.service;

import gift.entity.ProductEntity;
import gift.domain.ProductDTO;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public enum ProductServiceStatus {
        SUCCESS,
        NOT_FOUND,
        ERROR
    }

    private ProductDTO toProductDTO(ProductEntity productEntity) {
        return new ProductDTO(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getPrice(),
            productEntity.getImageUrl()
        );
    }

    private ProductEntity toProductEntity(ProductDTO productDTO) {
        return new ProductEntity(
            productDTO.getName(),
            productDTO.getPrice(),
            productDTO.getImageUrl()
        );
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> response = productRepository.findAll();
        return response.stream()
            .map(this::toProductDTO)
            .collect(Collectors.toList());
    }

    // Read(단일 상품) - getProduct()
    public Optional<ProductDTO> getProduct(Long id) {
        return productRepository.findById(id)
            .map(this::toProductDTO);
    }

    // Create(생성) - addProduct()
    public ProductServiceStatus createProduct(ProductDTO productDTO) {
        try {
            ProductEntity productEntity = toProductEntity(productDTO);
            productRepository.save(productEntity);
            return ProductServiceStatus.SUCCESS;
        } catch (Exception e) {
            return ProductServiceStatus.ERROR;
        }
    }

    // Update(수정) - updateProduct()
    public ProductServiceStatus editProduct(Long id, ProductDTO productDTO) {
        try {
            Optional<ProductEntity> existingProductEntityOpt = productRepository.findById(id);
            if (existingProductEntityOpt.isPresent()) {
                ProductEntity existingProductEntity = existingProductEntityOpt.get();
                existingProductEntity.update(
                    productDTO.getName(), productDTO.getPrice(), productDTO.getImageUrl());
                productRepository.save(existingProductEntity);
                return ProductServiceStatus.SUCCESS;
            }
            return ProductServiceStatus.NOT_FOUND;
        } catch (Exception e) {
            return ProductServiceStatus.ERROR;
        }
    }

    public ProductServiceStatus deleteProduct(Long id) {
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                return ProductServiceStatus.SUCCESS;
            }
            return ProductServiceStatus.NOT_FOUND;
        } catch (Exception e) {
            return ProductServiceStatus.ERROR;
        }
    }
}