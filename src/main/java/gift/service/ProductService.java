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
import org.springframework.beans.factory.annotation.Autowired;
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


    public Product createProduct(@Valid Product product) {
        if (product.getName().contains("카카오")) {
            throw new ForbiddenWordException("상품 이름에 '카카오'가 포함된 경우 담당 MD와 협의가 필요합니다.");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(@Valid Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(updatedProduct.getId());
        if (updatedProduct.getName().contains("카카오")) {
            throw new ForbiddenWordException("상품 이름에 '카카오'가 포함된 경우 담당 MD와 협의가 필요합니다.");
        }
        if (existingProduct.isEmpty()) {
            throw new ProductNotFoundException("수정할 상품이 존재하지 않습니다");
        }

        var product = existingProduct.get();
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setImageUrl(updatedProduct.getImageUrl());
        return productRepository.save(product);
    }

    public Product patchProduct(Long id, Map<String, Object> updates) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new ProductNotFoundException("수정할 상품이 존재하지 않습니다.");
        }
        applyUpdates(existingProduct.orElse(null), updates);
        return productRepository.save(existingProduct.get());
    }

    public List<Optional<Product>> patchProducts(List<Map<String, Object>> updatesList) {
        List<Optional<Product>> updatedProducts = new ArrayList<>();
        for (Map<String, Object> updates : updatesList) {
            try {
                Long id = ((Number) updates.get("id")).longValue();
                if (patchProduct(id, updates) != null) {
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

    public Product updateProductByName(String name, @Valid Product updatedProduct){
        var existingProduct = productRepository.findByName(name);
        if (existingProduct.isEmpty()) {
            throw new ProductNotFoundException("수정할 상품이 존재하지 않습니다!");
        }
        var product = existingProduct.get();
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setImageUrl(updatedProduct.getImageUrl());
        return productRepository.save(product);
    }
}
