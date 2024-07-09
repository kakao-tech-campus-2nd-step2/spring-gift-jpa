package gift.repository;

import gift.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByMemberId(Long memberId);
    void deleteByIdAndMemberId(Long id, Long memberId);
}
