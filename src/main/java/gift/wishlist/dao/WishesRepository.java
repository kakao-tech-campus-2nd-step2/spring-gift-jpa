package gift.wishlist.dao;

import gift.wishlist.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishesRepository extends JpaRepository<Wish, Long> {

    List<Wish> findByMember_Id(Long memberId);

    Optional<Wish> findByMember_IdAndProduct_Id(Long memberId, Long productId);

}