package gift.repository;
import gift.model.Member;
import gift.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<WishList, Long> {
    List<WishList> findByMember(Member member);

    //void deleteById(Long productId);
}
