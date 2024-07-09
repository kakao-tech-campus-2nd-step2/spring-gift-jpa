package gift.repository;

import gift.entityForJpa.Item;
import gift.entityForJpa.Member;
import gift.entityForJpa.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Wish findByMemberAndItem(Member member, Item item);
}
