package gift.service;

import gift.domain.Product;
import gift.dto.CreateProductDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final Map<Long, Product> products = new HashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    public Product createProduct(CreateProductDto productDto) {
        // 필수 필드 검증
        if (productDto.getName() == null || productDto.getPrice() == null || productDto.getDescription() == null) {
            throw new IllegalArgumentException("상품 정보를 모두 입력해야 합니다.");
        }

        Product product = new Product();
        product.setId(id.getAndIncrement());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        products.put(product.getId(), product);
        return product;
    }

}
