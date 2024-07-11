package gift.product.repository;

import gift.product.model.Member;
import gift.product.model.Wish;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface WishListRepository extends JpaRepository<Wish, Long> {
    Collection<Wish> findAllByMember(Member member);
}
