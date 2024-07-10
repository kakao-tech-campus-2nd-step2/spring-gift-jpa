package gift.repository;

import gift.model.Gift;
import gift.model.User;
import gift.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByUser(User user);

    List<Wish> findByUserAndGift(User user, Gift gift);

    void deleteByUserAndGift(User user, Gift gift);
}
