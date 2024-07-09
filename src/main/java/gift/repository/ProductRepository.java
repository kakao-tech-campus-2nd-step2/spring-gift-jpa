package gift.repository;

import gift.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Long, Product> {
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
}
