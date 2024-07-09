package gift.domain.product;

import gift.dto.ProductRequestDto;

import java.util.List;

public interface ProductRepository {
    void save(Product product);
    List<Product> findAll();
    Product findById(Long id);
    void deleteById(Long id);
    void update(Long id, ProductRequestDto dto);
    boolean isNotValidProductId(Long id);
}
