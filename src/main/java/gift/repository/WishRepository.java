package gift.repository;

import gift.entity.Product;
import gift.entity.Member;
import gift.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Wish findByMemberAndItem(Member member, Product product);
}
