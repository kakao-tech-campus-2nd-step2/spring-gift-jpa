package gift.repository;

import gift.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    boolean existsById(Long id);

    @Override
    Optional<Product> findById(Long id);

    @Override
    Object save(Product product);

    @Override
    void deleteById(Long id);
}
