package gift.domain.product;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReposiotory extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
}