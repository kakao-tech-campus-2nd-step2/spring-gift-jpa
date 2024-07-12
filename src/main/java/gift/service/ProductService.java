package gift.service;

import gift.entity.ProductEntity;
import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.List;
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

    private Product toProductDTO(ProductEntity productEntity) {
        return new Product(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getPrice(),
            productEntity.getImageUrl()
        );
    }

    private ProductEntity toProductEntity(Product product) {
        return new ProductEntity(
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        );
    }

    public List<Product> getAllProducts() {
        List<ProductEntity> response = productRepository.findAll();
        return response.stream()
            .map(this::toProductDTO)
            .collect(Collectors.toList());
    }

    // Read(단일 상품) - getProductEntity()
    public Product getProduct(Long id) {
        ProductEntity response = productRepository.findById(id).orElse(null);
        return response != null ? toProductDTO(response) : null;
    }

    // Create(생성) - addProduct()
    public ProductServiceStatus createProduct(Product product) {
        try {
            ProductEntity productEntity = toProductEntity(product);
            productRepository.save(productEntity);
            return ProductServiceStatus.SUCCESS;
        } catch (Exception e) {
            return ProductServiceStatus.ERROR;
        }
    }

    // Update(수정) - updateProduct()
    public ProductServiceStatus editProduct(Long id, Product product) {
        try {
            ProductEntity existingProductEntity = productRepository.findById(id).orElse(null);
            if (existingProductEntity != null) {
                existingProductEntity.update(product.getName(), product.getPrice(), product.getImageUrl());
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
            if (productRepository.findById(id).orElse(null) != null) {
                productRepository.deleteById(id);
                return ProductServiceStatus.SUCCESS;
            }
            return ProductServiceStatus.NOT_FOUND;
        } catch (Exception e) {
            return ProductServiceStatus.ERROR;
        }
    }
}
