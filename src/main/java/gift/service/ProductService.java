package gift.service;

import gift.exception.ForbiddenWordException;
import gift.exception.ProductNotFoundException;
import gift.model.Product;
import gift.repository.ProductRepository;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        return product.orElse(null);
    }


    public boolean createProduct(@Valid Product product) {
        if (product.getName().contains("카카오")) {
            throw new ForbiddenWordException("상품 이름에 '카카오'가 포함된 경우 담당 MD와 협의가 필요합니다.");
        }
        return productRepository.save(product) != null;
    }

    public boolean updateProduct(Long id, @Valid Product product) {
        if (product.getName().contains("카카오")) {
            throw new ForbiddenWordException("상품 이름에 '카카오'가 포함된 경우 담당 MD와 협의가 필요합니다.");
        }
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            existingProduct.get().setName(product.getName());
            existingProduct.get().setPrice(product.getPrice());
            existingProduct.get().setImageUrl(product.getImageUrl());
            return productRepository.save(existingProduct.get()) != null;
        }
        return false;
    }

    public boolean patchProduct(Long id, Map<String, Object> updates) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            applyUpdates(existingProduct.orElse(null), updates);
            return productRepository.save(existingProduct.get()) != null;
        }
        return false;
    }

    public List<Optional<Product>> patchProducts(List<Map<String, Object>> updatesList) {
        List<Optional<Product>> updatedProducts = new ArrayList<>();
        for (Map<String, Object> updates : updatesList) {
            try {
                Long id = ((Number) updates.get("id")).longValue();
                if (patchProduct(id, updates)) {
                    updatedProducts.add(productRepository.findById(id));
                }
            } catch (ProductNotFoundException | ForbiddenWordException ignored) {
            }
        }
        return updatedProducts;
    }

    private void applyUpdates(Product product, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            if (!"id".equals(key)) {
                updateProductField(product, key, value);
            }
        });
        if (product.getName().contains("카카오")) {
            throw new ForbiddenWordException("상품 이름에 '카카오'가 포함된 경우 담당 MD와 협의가 필요합니다.");
        }
    }

    private void updateProductField(Product product, String key, Object value) {
        if ("name".equals(key)) {
            product.setName((String) value);
            return;
        }
        if ("price".equals(key)) {
            product.setPrice((Integer) value);
            return;
        }
        if ("imageUrl".equals(key)) {
            product.setImageUrl((String) value);
            return;
        }
        throw new IllegalArgumentException("Invalid field: " + key);
    }


    public boolean deleteProduct(Long id) {
        productRepository.deleteById(id);
        return productRepository.findById(id).isEmpty();
    }
}
