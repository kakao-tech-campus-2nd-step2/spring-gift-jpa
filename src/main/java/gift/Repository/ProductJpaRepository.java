package gift.Repository;

import gift.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Products, Long> {
    List<Products> findByisDeletedFalse();
}
