package gift.repository;

import gift.vo.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Integer> {
    List<Wish> findByMemberId(Long memberId);
}
