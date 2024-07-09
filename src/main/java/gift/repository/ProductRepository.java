package gift.repository;

import gift.domain.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>  {
     Optional<Product> findByNameAndPriceAndImageUrl(String name, Long price, String imageUrl);
}
