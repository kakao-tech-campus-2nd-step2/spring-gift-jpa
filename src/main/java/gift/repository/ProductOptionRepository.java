package gift.repository;

import gift.model.ProductOption;

import java.util.List;

public interface ProductOptionRepository {
    ProductOption save(ProductOption productOption);

    void update(ProductOption productOption);

    ProductOption findById(Long id);

    List<ProductOption> findAll(Long productId);

    void deleteById(Long id);

    void deleteByProductId(Long id);
}
