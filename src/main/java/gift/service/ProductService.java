package gift.service;

import gift.entity.ProductEntity;
import gift.domain.ProductDTO;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            productDTO.getId(),
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
    @Transactional
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
    @Transactional
    public ProductServiceStatus editProduct(Long id, ProductDTO productDTO) {
        try {
            Optional<ProductEntity> existingProductEntityOptional = productRepository.findById(id);
            if (!existingProductEntityOptional.isPresent()) {
                return ProductServiceStatus.NOT_FOUND;
            }
            ProductEntity existingProductEntity = existingProductEntityOptional.get();
            ProductEntity updatedProductEntity = new ProductEntity(
                existingProductEntity.getId(),
                productDTO.getName(),
                productDTO.getPrice(),
                productDTO.getImageUrl()
            );
            productRepository.save(updatedProductEntity);
            return ProductServiceStatus.SUCCESS;
        } catch (Exception e) {
            return ProductServiceStatus.ERROR;
        }
    }

    @Transactional
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