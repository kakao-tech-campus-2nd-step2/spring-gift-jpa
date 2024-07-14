package gift.repository;

import gift.entity.Product;
import gift.entity.Member;
import gift.entity.Wish;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    Optional<Wish> findByMemberAndProduct(Member member, Product product);

    void deleteByMemberAndProductId(Member member, Long productId);

    void deleteByProductId(Long productId);
}
