package gift.service;

import gift.exception.ForbiddenWordException;
import gift.exception.ProductNotFoundException;
import gift.model.Product;
import gift.repository.ProductRepository;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        return product;
    }


    public boolean createProduct(@Valid Product product) {
        if (product.getName().contains("카카오")) {
            throw new ForbiddenWordException("상품 이름에 '카카오'가 포함된 경우 담당 MD와 협의가 필요합니다.");
        }
        return productRepository.save(product);
    }

    public boolean updateProduct(Long id, @Valid Product product) {
        if (product.getName().contains("카카오")) {
            throw new ForbiddenWordException("상품 이름에 '카카오'가 포함된 경우 담당 MD와 협의가 필요합니다.");
        }
        Product existingProduct = productRepository.findById(id);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setImageUrl(product.getImageUrl());
            return productRepository.update(existingProduct);
        }
        return false;
    }

    public boolean patchProduct(Long id, Map<String, Object> updates) {
        Product existingProduct = productRepository.findById(id);
        if (existingProduct != null) {
            applyUpdates(existingProduct, updates);
            return productRepository.update(existingProduct);
        }
        return false;
    }

    public List<Product> patchProducts(List<Map<String, Object>> updatesList) {
        List<Product> updatedProducts = new ArrayList<>();
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
        return productRepository.delete(id);
    }

    public List<Product> getProducts(int page, int size) {
        return productRepository.findPaginated(page, size);
    }
}
