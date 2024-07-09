package gift.repository;

import gift.model.UserGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WishRepository extends JpaRepository<UserGift,Long> {
    List<UserGift> findByUserId(Long userId);

    void deleteByUserIdAndGiftId(Long userId, Long giftId);

}
