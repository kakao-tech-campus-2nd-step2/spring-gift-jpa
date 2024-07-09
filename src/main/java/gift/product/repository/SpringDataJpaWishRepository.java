package gift.product.repository;

import gift.product.dto.LoginMember;
import gift.product.model.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaWishRepository extends JpaRepository<Wish, Long>, WishRepository {

}
