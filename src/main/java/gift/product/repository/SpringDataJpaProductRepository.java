package gift.product.repository;

import gift.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaProductRepository extends JpaRepository<Product, Long>,
    ProductRepository {

}
