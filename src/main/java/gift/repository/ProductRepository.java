package gift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gift.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {


}

