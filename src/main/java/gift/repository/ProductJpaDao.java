package gift.repository;

import gift.dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaDao extends JpaRepository<Product, Long> {

}
