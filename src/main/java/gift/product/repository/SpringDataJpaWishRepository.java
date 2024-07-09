package gift.product.repository;

import gift.product.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaWishRepository extends JpaRepository<Wish, Long>, WishRepository {

}
