package gift.repository.product;

import gift.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductSpringDataJpaRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}