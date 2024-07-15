package gift.repository;


import gift.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndNameAndPriceAndImageUrl(Long id, String name, Integer price, String imageUrl);
}
