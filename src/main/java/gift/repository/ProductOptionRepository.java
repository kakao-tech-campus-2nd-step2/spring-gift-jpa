package gift.repository;

import gift.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    ProductOption save(ProductOption productOption);

    Optional<ProductOption> findById(Long id);

    List<ProductOption> findAllByProductId(Long productId);

    void deleteById(Long id);
}
