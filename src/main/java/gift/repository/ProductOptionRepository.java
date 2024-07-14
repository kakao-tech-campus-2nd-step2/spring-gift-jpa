package gift.repository;

import gift.model.ProductOption;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    List<ProductOption> findAllByProductId(Long productId, Pageable pageable);

    void deleteProductOptionsByProductId(Long productId);
}
