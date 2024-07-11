package gift.api.wishlist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, WishId> {
    List<Wish> findByMemberId(Long memberId);
}
