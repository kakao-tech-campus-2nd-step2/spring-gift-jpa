package gift.service;

import gift.domain.Product;
import gift.dto.CreateProductDto;
import gift.dto.UpdateProductDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final Map<Long, Product> products = new HashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    public Product createProduct(CreateProductDto productDto) {
        Product product = new Product();
        product.setId(id.getAndIncrement());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        products.put(product.getId(), product);
        return product;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Product getProduct(Long productId) {
        Product product = products.get(productId);
        if (product == null) {
            throw new IllegalArgumentException("일치하는 상품이 없습니다.");
        }
        return products.get(productId);
    }

    public Product updateProduct(Long productId, UpdateProductDto productDto) {
        Product product = products.get(productId);
        if (product == null) {
            throw new IllegalArgumentException("일치하는 상품이 없습니다.");
        }

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        products.put(productId, product);
        return product;
    }
}
