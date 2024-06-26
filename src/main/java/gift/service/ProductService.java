package gift.service;

import gift.domain.Product;
import gift.dto.CreateProductDto;
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
        return products.get(productId);
    }
}
