package gift.product.model;

import gift.product.model.dto.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndIsActiveTrue(Long id);

    // isActive가 true인 모든 제품 찾기
    List<Product> findAllByIsActiveTrue();
}
