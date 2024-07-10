package gift.repository;

import gift.model.ProductOption;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOptionRepository extends BaseRepository<ProductOption, Long> {
    ProductOption save(ProductOption productOption);

    Optional<ProductOption> findById(Long id);

    List<ProductOption> findAllByProductId(Long productId);

    void deleteById(Long id);
}
