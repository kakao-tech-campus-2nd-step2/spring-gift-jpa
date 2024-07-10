package gift.repository;

import gift.model.ProductOption;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends BaseRepository<ProductOption, Long> {
    List<ProductOption> findAllByProductId(Long productId);
}
